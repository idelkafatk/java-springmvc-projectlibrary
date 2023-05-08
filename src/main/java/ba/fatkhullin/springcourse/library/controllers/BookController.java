package ba.fatkhullin.springcourse.library.controllers;

import ba.fatkhullin.springcourse.library.model.Book;
import ba.fatkhullin.springcourse.library.model.Person;
import ba.fatkhullin.springcourse.library.services.BooksService;
import ba.fatkhullin.springcourse.library.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String showBooks(Model model) {

        model.addAttribute("books", booksService.showBooks());

        return "books/index";
    }

    @GetMapping("/{bookId}")
    public String showBook(Model model, @PathVariable("bookId") int bookId,
                           @ModelAttribute("person") Person person) {

        Person bookOwner = booksService.getBookOwner(bookId);

        model.addAttribute("book", booksService.showBook(bookId));

        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", peopleService.showPeople());

        return "books/info";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {

        return "books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{bookID}/edit")
    public String edit(@PathVariable("bookID") int bookID,
                       Model model) {
        model.addAttribute("book", booksService.showBook(bookID));

        return "books/edit";
    }

    @PatchMapping("/{bookId}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("bookId") int bookId) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        booksService.update(bookId, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        booksService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("{bookId}")
    public String delete(@PathVariable("bookId") int bookId) {
        booksService.delete(bookId);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}/release")
    public String release(@PathVariable("bookId") int bookId) {
        booksService.release(bookId);
        return "redirect:/books/" + bookId;
    }
}
