package jantoni1.librarybackend.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jantoni1.librarybackend.model.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = BookDeserializer.class)
@Builder
public class BookDTO {

    String isbn;

    String title;

    String coverUrl;

    Integer numberOfPages;

    String authors;

    String publishDate;

    String publishers;

    public BookDTO(BookEntity bookEntity) {
        isbn = bookEntity.getIsbn();
    }

}
