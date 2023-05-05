package ba.fatkhullin.springcourse.library.controllers;

import ba.fatkhullin.springcourse.library.dao.BookDAO;
import ba.fatkhullin.springcourse.library.dao.PersonDAO;
import ba.fatkhullin.springcourse.library.model.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String showPeople(Model model) {

        model.addAttribute("people", personDAO.showPeople());

        return "people/index";
    }

    @GetMapping("/{personId}")
    public String showPerson(Model model,
                             @PathVariable("personId") int personId) {

        model.addAttribute("books", bookDAO.getBooksInPersonHands(personId));
        model.addAttribute("person", personDAO.showPerson(personId));

        return "people/info";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") @Valid Person person) {

        return "people/new";
    }

    @PostMapping()
    public String addPerson(@ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{personId}/edit")
    public String edit(@PathVariable("personId") int personId,
                       Model model) {
        model.addAttribute("person", personDAO.showPerson(personId));

        return "people/edit";
    }

    @PatchMapping("/{personId}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("personId") int personId) {

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        personDAO.update(personId, person);
        return "redirect:/people";
    }

    @DeleteMapping("{personId}")
    public String delete(@PathVariable("personId") int personId) {
        personDAO.delete(personId);
        return "redirect:/people";
    }

}
