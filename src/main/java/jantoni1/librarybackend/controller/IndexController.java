package jantoni1.librarybackend.controller;

import jantoni1.librarybackend.domain.BookDTO;
import jantoni1.librarybackend.domain.BookDetailsDTO;
import jantoni1.librarybackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
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
    public void saveBook(@RequestBody BookDetailsDTO bookDetailsDTO) {
        bookService.saveBook(bookDetailsDTO);
    }

    @GetMapping("/book")
    public ResponseEntity<BookDetailsDTO> getBook(@RequestParam String isbn)  {
        return ResponseEntity.ok(bookService.getBook(isbn));
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getUserBooks() {
        return ResponseEntity.ok(bookService.getLoggedUserBooks());
    }

    @PostMapping("/dummy")
    public String saveDummy() {
        var bookDTO = new BookDetailsDTO();
        bookDTO.setTitle("Harry Pothead");
        bookDTO.setIsbn("123454321");
        bookService.saveBook(bookDTO);
        return bookDTO.getIsbn();
    }

}