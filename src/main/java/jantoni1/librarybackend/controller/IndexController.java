package jantoni1.librarybackend.controller;

import jantoni1.librarybackend.domain.BookDTO;
import jantoni1.librarybackend.exception.BookNotFoundException;
import jantoni1.librarybackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.OK)
    public void saveBook(@RequestBody BookDTO bookDTO) {
        bookService.saveBook(bookDTO);
    }

    @GetMapping("/book")
    public ResponseEntity<BookDTO> getBook(@RequestParam String isbn) throws BookNotFoundException {
        return ResponseEntity.ok(bookService.getBook(isbn));
    }

    @PostMapping("/dummy")
    public String saveDummy() {
        var bookDTO = new BookDTO();
        bookDTO.setTitle("Harry Pothead");
        bookDTO.setIsbn("123454321");
        bookService.saveBook(bookDTO);
        return bookDTO.getIsbn();
    }

}