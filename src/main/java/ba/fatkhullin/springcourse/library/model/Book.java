package ba.fatkhullin.springcourse.library.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int bookId;

    @NotEmpty(message = "Введите название книги")
    private String bookName;
    private Integer personId;

    @NotEmpty(message = "Введите автора книги")
    private String author;

    @Max(value = 2023, message = "Год выпуска книги должен быть от 0 до 2023")
    private int year;

    public Book(int bookId, String bookName, Integer personId, String author, int year) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.personId = personId;
        this.author = author;
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book() {
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

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}
