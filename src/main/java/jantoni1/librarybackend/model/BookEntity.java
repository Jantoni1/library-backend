package jantoni1.librarybackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Table(name = "books")
@Entity
public class BookEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    private Integer id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "description")
    private String description;

    @Column(name = "add_date")
    private Date addDate;

}
