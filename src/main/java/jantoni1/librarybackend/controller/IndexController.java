package jantoni1.librarybackend.controller;

import jantoni1.librarybackend.domain.BookDTO;
import jantoni1.librarybackend.domain.BookDetailsDTO;
import jantoni1.librarybackend.exception.BookException;
import jantoni1.librarybackend.security.UserContext;
import jantoni1.librarybackend.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping(value = "/")
@Slf4j
public class IndexController {

    private final BookService bookService;

    @Autowired
    public IndexController(BookService bookService) {
        this.bookService = bookService;
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