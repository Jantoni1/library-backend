package jantoni1.librarybackend.service;

import jantoni1.librarybackend.domain.BookDTO;
import jantoni1.librarybackend.exception.BookNotFoundException;
import jantoni1.librarybackend.model.BookEntity;
import jantoni1.librarybackend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO getBook(String isbn) throws BookNotFoundException {
        return new BookDTO(bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new));
    }

    public void saveBook(BookDTO bookDTO) {
        var book = new BookEntity();
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        bookRepository.save(book);
    }

}

