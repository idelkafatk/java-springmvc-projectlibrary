package ba.fatkhullin.springcourse.library.controllers;

import ba.fatkhullin.springcourse.library.dao.BookDAO;
import ba.fatkhullin.springcourse.library.dao.PersonDAO;
import ba.fatkhullin.springcourse.library.model.Book;
import ba.fatkhullin.springcourse.library.model.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String showBooks(Model model) {

        model.addAttribute("books", bookDAO.showBooks());

        return "books/index";
    }

    @GetMapping("/{bookId}")
    public String showBook(Model model, @PathVariable("bookId") int bookId,
                           @ModelAttribute("person") @Valid Person person) {

        Optional<Person> bookOwner = bookDAO.getBookOwner(bookId);

        model.addAttribute("book", bookDAO.showBook(bookId));

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.showPeople());

        return "books/info";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") @Valid Book book) {

        return "books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{bookID}/edit")
    public String edit(@PathVariable("bookID") int bookID,
                       Model model) {
        model.addAttribute("book", bookDAO.showBook(bookID));

        return "books/edit";
    }

    @PatchMapping("/{bookId}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("bookId") int bookId) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookDAO.update(bookId, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person selectedPerson) {
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("{bookId}")
    public String delete(@PathVariable("bookId") int bookId) {
        bookDAO.delete(bookId);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}/release")
    public String release(@PathVariable("bookId") int bookId) {
        bookDAO.release(bookId);
        return "redirect:/books/" + bookId;
    }
}
