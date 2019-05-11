package jantoni1.librarybackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "books")
public class BookEntity {

    @Id
    @Column(name = "id")
    Integer id;

    @Column(name = "isbn")
    String isbn;

    @Column(name = "title")
    String title;

}
