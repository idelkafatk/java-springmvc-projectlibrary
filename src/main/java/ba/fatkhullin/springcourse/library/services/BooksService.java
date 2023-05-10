package ba.fatkhullin.springcourse.library.services;

import ba.fatkhullin.springcourse.library.model.Book;
import ba.fatkhullin.springcourse.library.model.Person;
import ba.fatkhullin.springcourse.library.repositories.BooksRepository;
import ba.fatkhullin.springcourse.library.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
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

    public List<Book> showBooks(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear && page != null && booksPerPage != null) {
            return booksRepository.
                    findAll(PageRequest.of(page - 1, booksPerPage, Sort.by("year")))
                    .getContent();
        } else if (sortByYear){
            return booksRepository.findAll(Sort.by("year"));
        } else if (page != null && booksPerPage != null){
            return booksRepository.findAll(PageRequest.of(page - 1, booksPerPage)).getContent();
        } else {
            return booksRepository.findAll();
        }
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
            Hibernate.initialize(bookOwner.getBooks());

            for (Book book : bookOwner.getBooks()) {
                long currentDateMillis = new Date().getTime();
                long assignedAtMillis = book.getAssignedAt().getTime();
                long daysSinceAssigned = (currentDateMillis - assignedAtMillis) / (1000 * 60 * 60 * 24);

                if (daysSinceAssigned > 10) {
                    book.setMissedBookReturn(true);
                }
            }

            return booksRepository.findBooksByOwner(bookOwner);
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional
    public void assign(int bookId, Person selectedPerson) {
        Optional<Book> optionalBook = booksRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book bookToBeUpdated = optionalBook.get();
            bookToBeUpdated.setOwner(selectedPerson);
            bookToBeUpdated.setAssignedAt(new Date());

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
            bookToBeUpdated.setAssignedAt(null);
        } else {
            throw new IllegalArgumentException("Книга не найдена");
        }
    }

    public Person getBookOwner(int bookId) {
        return booksRepository.findById(bookId).map(Book::getOwner).orElse(null);
    }

    public List<Book> searchBooks(String name) {

        return booksRepository.findBooksByBookNameStartingWithIgnoreCase(name);
    }
}
