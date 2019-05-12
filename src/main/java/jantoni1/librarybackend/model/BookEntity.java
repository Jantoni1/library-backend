package jantoni1.librarybackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "books")
@Entity
public class BookEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    Integer id;

    @Column(name = "isbn")
    String isbn;

    @Column(name = "title")
    String title;

}
