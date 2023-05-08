package ba.fatkhullin.springcourse.library.services;

import ba.fatkhullin.springcourse.library.model.Book;
import ba.fatkhullin.springcourse.library.model.Person;
import ba.fatkhullin.springcourse.library.repositories.BooksRepository;
import ba.fatkhullin.springcourse.library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> showBooks() {
        return booksRepository.findAll();
    }

    public Book showBook(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBookId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public List<Book> getBooksInPersonHands(int owner) {
        Optional<Person> person = peopleRepository.findById(owner);
        if (person.isPresent()) {
            Person bookOwner = person.get();
            return booksRepository.findBooksByOwner(bookOwner);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void assign(int bookId, Person selectedPerson) {
        Optional<Book> optionalBook = booksRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book bookToBeUpdated = optionalBook.get();
            bookToBeUpdated.setOwner(selectedPerson);

            booksRepository.save(bookToBeUpdated);
        } else {
            throw new IllegalArgumentException("Книга не найдена");
        }
    }

    @Transactional
    public void release(int bookId) {
        Optional<Book> optionalBook = booksRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book bookToBeUpdated = optionalBook.get();
            bookToBeUpdated.setOwner(null);

            booksRepository.save(bookToBeUpdated);
        } else {
            throw new IllegalArgumentException("Книга не найдена");
        }
    }

    public Person getBookOwner(int bookId) {
        Optional<Book> optionalBook = booksRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return book.getOwner();
        } else {
            return null;
        }
    }
}
