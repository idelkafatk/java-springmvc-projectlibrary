package ba.fatkhullin.springcourse.library.controllers;

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
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final BooksService booksService;
    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String showPeople(Model model) {

        model.addAttribute("people", peopleService.showPeople());

        return "people/index";
    }

    @GetMapping("/{personId}")
    public String showPerson(Model model,
                             @PathVariable("personId") int personId) {

        model.addAttribute("books", booksService.getBooksInPersonHands(personId));
        model.addAttribute("person", peopleService.showPerson(personId));

        return "people/info";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {

        return "people/new";
    }

    @PostMapping()
    public String addPerson(@ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{personId}/edit")
    public String edit(@PathVariable("personId") int personId,
                       Model model) {
        model.addAttribute("person", peopleService.showPerson(personId));

        return "people/edit";
    }

    @PatchMapping("/{personId}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("personId") int personId) {

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        peopleService.update(personId, person);
        return "redirect:/people";
    }

    @DeleteMapping("{personId}")
    public String delete(@PathVariable("personId") int personId) {
        peopleService.delete(personId);
        return "redirect:/people";
    }

}
