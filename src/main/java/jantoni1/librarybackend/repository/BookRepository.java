package jantoni1.librarybackend.repository;

import jantoni1.librarybackend.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    Optional<BookEntity> findByIsbn(String isbn);

}
