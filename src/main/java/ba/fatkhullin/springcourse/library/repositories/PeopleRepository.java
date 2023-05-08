package ba.fatkhullin.springcourse.library.repositories;

import ba.fatkhullin.springcourse.library.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
