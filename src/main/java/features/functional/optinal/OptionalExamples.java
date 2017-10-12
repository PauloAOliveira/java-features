package features.functional.optinal;

import features.functional.domain.City;
import features.functional.domain.Genre;
import features.functional.domain.Person;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

public class OptionalExamples {

    private static final Random RANDOM = new Random();
    private Optional<Person> person;

    public OptionalExamples(Person person) {
        this.person = Optional.ofNullable(person);
    }

    public Optional<Person> getPerson() {
        return person;
    }

    public void setCityIfPresent(City city) {
        person.ifPresent(p -> p.setCity(city));
    }

    public Person getIfAgeLessThanOrElse(Integer age, Person other) {
        return person.filter(p -> p.getAge() < age).orElse(other);
    }

    public Optional<String> getNameIfPresent() {
        return person.map(Person::getName);
    }

    public Optional<String> getCityNameIfPresent() {
        return person.flatMap(Person::getCity).map(City::getName);
    }
}
