package jantoni1.librarybackend.service;

import jantoni1.librarybackend.domain.BookDTO;
import jantoni1.librarybackend.exception.BookNotFoundException;
import jantoni1.librarybackend.model.BookEntity;
import jantoni1.librarybackend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Value("${open.book.api.key}")
    private String API_URL;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO getBook(String isbn) throws BookNotFoundException {
        return Optional.ofNullable(bookRepository.findByIsbn(isbn)
                .map(BookDTO::new)
                .orElseGet(() -> findBookExternally(isbn)))
                .orElseThrow(BookNotFoundException::new);
    }

    private BookDTO findBookExternally(String isbn)  {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BookDTO> response = restTemplate.getForEntity(buildApiCallUrl(isbn), BookDTO.class);
        return response.getBody();
    }

    private String buildApiCallUrl(String isbn) {
        return API_URL.concat("?bibkeys=ISBN:".concat(isbn).concat("&format=json&jscmd=data"));
    }

    public void saveBook(BookDTO bookDTO) {
        var book = new BookEntity();
        book.setIsbn(bookDTO.getIsbn());
        bookRepository.save(book);
    }


}

