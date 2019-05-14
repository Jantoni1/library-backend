package jantoni1.librarybackend.domain;

import jantoni1.librarybackend.model.BookEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookDTO extends BookDetailsDTO {

    String description;

    Date addDate;

    public BookDTO(BookEntity bookEntity, BookDetailsDTO bookDetailsDTO) {
        super(bookDetailsDTO);
        description = bookEntity.getDescription();
        addDate = bookEntity.getAddDate();
    }

}
