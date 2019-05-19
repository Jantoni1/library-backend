package jantoni1.librarybackend.domain;

import jantoni1.librarybackend.model.UserBookEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookDTO extends BookDetailsDTO {

    String description;

    Date addDate;

    public BookDTO(UserBookEntity userBookEntity, BookDetailsDTO bookDetailsDTO) {
        super(bookDetailsDTO);
        description = userBookEntity.getDescription();
        addDate = userBookEntity.getAddDate();
    }

}
