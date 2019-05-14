package jantoni1.librarybackend.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@JsonDeserialize(using = BookListDeserializer.class)
public class BookListDTO  extends ArrayList<BookDetailsDTO> {

    public BookListDTO() {
    }

    public BookListDTO(Collection<? extends BookDetailsDTO> c) {
        super(c);
    }

    public Optional<BookDetailsDTO> getOneBook() {
        if(!isEmpty()) {
            return Optional.of(get(0));
        }
        else return Optional.empty();
    }

}
