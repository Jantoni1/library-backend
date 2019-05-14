package jantoni1.librarybackend.service;

import jantoni1.librarybackend.constants.Constants;
import jantoni1.librarybackend.domain.BookDTO;
import jantoni1.librarybackend.domain.BookDetailsDTO;
import jantoni1.librarybackend.domain.BookListDTO;
import jantoni1.librarybackend.model.BookEntity;
import jantoni1.librarybackend.model.UserBookEntity;
import jantoni1.librarybackend.model.UserEntity;
import jantoni1.librarybackend.repository.BookRepository;
import jantoni1.librarybackend.repository.UserBookRepository;
import jantoni1.librarybackend.security.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Value("${open.book.api.key}")
    private String API_URL;

    private final BookRepository bookRepository;

    private final UserBookRepository userBookRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UserBookRepository userBookRepository) {
        this.bookRepository = bookRepository;
        this.userBookRepository = userBookRepository;
    }

    public BookDetailsDTO getBook(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(BookDetailsDTO::new)
                .orElseGet(() -> runSingleBookExternalSearch(isbn));
    }

    private BookDetailsDTO runSingleBookExternalSearch(String isbn) {
        return Objects.requireNonNull(Optional
            .ofNullable(runExternalBooksSearch(Collections.singletonList(isbn)).getBody())
                .map(BookListDTO::getOneBook)
                .orElse(null))
            .orElse(null);
    }

    private ResponseEntity<BookListDTO> runExternalBooksSearch(List<String> isbnCodes) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(buildApiCallUrl(isbnCodes), BookListDTO.class);
    }

    private String buildApiCallUrl(List<String> isbnCodes) {
        return API_URL.concat("?bibkeys=").concat(isbnCodes.stream()
                .reduce((isbn1, isbn2) -> isbn1.concat(",").concat(Constants.ISBN_KEY_SUBSTRING).concat(isbn2)).orElse(""))
                .concat("&format=json&jscmd=data");
    }

    public List<BookDTO> getUserBooks(Integer userId) {

        var books = userBookRepository.getAllByUser_Id(userId)
                .stream()
                .map(UserBookEntity::getBook)
                .collect(Collectors.toList());

        return getBooksDetails(books);

    }

    public List<BookDTO> getLoggedUserBooks() {
        return getUserBooks(UserContext.getUser().getId());
    }

    List<BookDTO> getBooksDetails(List<BookEntity> bookEntities) {

        var booksDetails =  Optional.ofNullable(runExternalBooksSearch(bookEntities
                .stream()
                .map(BookEntity::getIsbn)
                .collect(Collectors.toList())).getBody()).orElseGet(BookListDTO::new)
                .stream()
                .collect(Collectors.toMap(
                        BookDetailsDTO::getIsbn,
                        (bookDetails) -> bookDetails,
                        (book1, book2) -> book1));

        return bookEntities.stream()
                .map(bookEntity -> new BookDTO(bookEntity, booksDetails.get(bookEntity.getIsbn())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveBook(BookDetailsDTO bookDetailsDTO) {
        var user = new UserEntity();
        user.setId(UserContext.getUser().getId());
        var book = bookRepository.findByIsbn(bookDetailsDTO.getIsbn())
                .orElseGet(() -> createBook(bookDetailsDTO.getIsbn()));
        var userBook = new UserBookEntity();
        userBook.setBook(book);
        userBook.setUser(user);
        userBook.setObtainDate(bookDetailsDTO.getObtainDate());
        userBook.setDescription(bookDetailsDTO.getDescription());
        userBookRepository.save(userBook);
    }

    private BookEntity createBook(String isbn) {
        var bookEntity = new BookEntity();
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

}

