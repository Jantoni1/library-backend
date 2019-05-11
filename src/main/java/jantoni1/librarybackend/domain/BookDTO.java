package jantoni1.librarybackend.domain;

import jantoni1.librarybackend.model.BookEntity;
import lombok.Data;

@Data
public class BookDTO {

    String isbn;

    String title;

    public BookDTO(BookEntity bookEntity) {
        isbn = bookEntity.getIsbn();
        title = bookEntity.getTitle();
    }

    public BookDTO() {
    }
}
