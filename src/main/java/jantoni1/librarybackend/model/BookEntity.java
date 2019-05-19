package jantoni1.librarybackend.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Table(name = "books")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    private Integer id;

    @Column(name = "isbn")
    private String isbn;

}
