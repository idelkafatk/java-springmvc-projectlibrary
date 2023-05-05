package ba.fatkhullin.springcourse.library.model;

import jakarta.validation.constraints.*;

public class Person {

    private int personId;

    @NotEmpty(message = "Заполните поле ФИО")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+",
            message = "Введите ФИО в формате: Иванов Иван Иванович")
    private String fullName;

    @Min(value = 1900, message = "Год рождения читателя должен быть больше 1900")
    @Max(value = 2023, message = "Год рождения читателя должен быть не больше 2023")
    private int yearOfBirth;

    public Person() {
    }

    public Person(int personId, String fullName, int yearOfBirth) {
        this.personId = personId;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
