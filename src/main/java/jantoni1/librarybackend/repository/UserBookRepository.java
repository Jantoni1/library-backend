package jantoni1.librarybackend.repository;
import jantoni1.librarybackend.model.UserBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBookEntity, Integer> {

    List<UserBookEntity> getAllByUser_Id(Integer user_id);

}
