package ba.fatkhullin.springcourse.library.repositories;

import ba.fatkhullin.springcourse.library.model.Book;
import ba.fatkhullin.springcourse.library.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findBooksByOwner(Person owner);
}
