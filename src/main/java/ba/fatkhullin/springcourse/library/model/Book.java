package ba.fatkhullin.springcourse.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @NotEmpty(message = "Введите название книги")
    @Column(name = "book_name")
    private String bookName;

    @NotEmpty(message = "Введите автора книги")
    @Column(name = "author")
    private String author;

    @Max(value = 2023, message = "Год выпуска книги должен быть от 0 до 2023")
    @Column(name = "year")
    private int year;

    public Book() {
    }

    public Book(String bookName, Person owner, String author, int year) {
        this.owner = owner;
        this.bookName = bookName;
        this.author = author;
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
