package ba.fatkhullin.springcourse.library.dao;

import ba.fatkhullin.springcourse.library.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public PersonDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Person> showPeople() {
//        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
//    }
//
//    public Person showPerson(int personId) {
//        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id = ?", new Object[]{personId},
//                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
//    }
//
//    public void save(Person person) {
//        jdbcTemplate.update("INSERT INTO Person(fullname, year_of_birth) VALUES (?, ?)", person.getFullName(),
//                person.getYearOfBirth());
//    }
//
//    public void update(int personId, Person updatedPerson) {
//        jdbcTemplate.update("UPDATE Person SET fullname=?, year_of_birth=? WHERE person_id = ?",
//                updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), personId);
//    }
//
//    public void delete(int personId) {
//        jdbcTemplate.update("DELETE FROM Person WHERE person_id = ?", personId);
//    }
}
