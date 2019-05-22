package jantoni1.librarybackend.domain;

import jantoni1.librarybackend.model.UserBookEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO extends BookDetailsDTO {

    String description;

    Date addDate;

    public BookDTO(UserBookEntity userBookEntity, BookDetailsDTO bookDetailsDTO) {
        super(bookDetailsDTO);
        description = userBookEntity.getDescription();
        addDate = userBookEntity.getAddDate();
    }

}
