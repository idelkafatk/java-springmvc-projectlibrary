package ba.fatkhullin.springcourse.library.dao;

import ba.fatkhullin.springcourse.library.model.Book;
import ba.fatkhullin.springcourse.library.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showBooks() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book showBook(int bookId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id = ?", new Object[]{bookId},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book (book_name, author, year) VALUES (?, ?, ?)",
                book.getBookName(), book.getAuthor(), book.getYear());
    }

    public void update(int bookId, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET person_id = ?, book_name=?, author=?, year=? WHERE book_id=?",
                updatedBook.getPersonId(), updatedBook.getBookName(), updatedBook.getAuthor(), updatedBook.getYear(), bookId);
    }

    public Optional<Person> getBookOwner(int bookId) {
        // Выбираем все колонки таблицы Person из объединенной таблицы
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.person_id " +
                        "WHERE Book.book_id = ?", new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public List<Book> getBooksInPersonHands(int personId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{personId},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void assign(int bookId, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", selectedPerson.getPersonId(), bookId);
    }

    public void release(int bookId) {
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE book_id=?", bookId);
    }

    public void delete(int bookId) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", bookId);
    }
}
