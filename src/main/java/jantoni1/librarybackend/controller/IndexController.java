package jantoni1.librarybackend.controller;

import jantoni1.librarybackend.domain.BookDTO;
import jantoni1.librarybackend.domain.BookDetailsDTO;
import jantoni1.librarybackend.exception.BookException;
import jantoni1.librarybackend.security.UserContext;
import jantoni1.librarybackend.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
@Slf4j
public class IndexController {

    private final BookService bookService;

    @Autowired
    public IndexController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index() {
        return "Hello there! I'm running.";
    }

    @GetMapping("/book")
    public ResponseEntity<BookDetailsDTO> getBook(@RequestParam String isbn)  {
        return ResponseEntity.ok(bookService.getBook(isbn));
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Integer> addBook(@RequestBody BookDTO bookDTO) throws BookException {
        log.info("Started adding a book with ISBN {} for user with ID {}", bookDTO.getIsbn(), UserContext.getUser().getId());
        var savedBook = bookService.addBook(bookDTO, UserContext.getUser());
        log.info("Successfully finished adding a book with ISBN {} for user with ID {}", bookDTO.getIsbn(), UserContext.getUser().getId());
        return ResponseEntity.ok(savedBook.getId());
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getUserBooks() {
        return ResponseEntity.ok(bookService.getLoggedUserBooks());
    }

    @PostMapping("/dummy")
    public BookDetailsDTO saveDummy() {
        var bookDTO = new BookDetailsDTO();
        bookDTO.setTitle("Harry Pothead");
        bookDTO.setIsbn("123454321");
        bookService.saveBook(bookDTO);
        return bookDTO;
    }

}