package features.functional.optinal;

import features.functional.domain.City;
import features.functional.domain.Genre;
import features.functional.domain.Person;

import java.util.Optional;

public class OptionalExamples {

    private Optional<Person> person;

    public OptionalExamples() {
        person = Optional.empty();
    }

    public OptionalExamples(Person person) {
        this.person = Optional.ofNullable(person);
    }

    public Optional<Person> getOptionalPerson() {
        return person;
    }

    public Person getPerson() {
        return person.orElseThrow(() -> new IllegalStateException("There is no person"));
    }

    /**
     * Works only on Java 9
     * */
    public Optional<Person> getPersonOrDefault() {
        return person.or(() -> Optional.of(getDefault()));
    }



    public void setCityIfPresent(City city) {
        person.ifPresent(p -> p.setCity(city));
    }

    /**
     * Works only on Java 9
     * */
    public void setCityIfPresentOrCreateDefault(City city) {
        person.ifPresentOrElse(p -> p.setCity(city), () -> {
            Person def = getDefault();
            def.setCity(city);
            person = Optional.of(def);
        });
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

    private Person getDefault() {
        return new Person("default", 1, Genre.FEMALE);
    }
}
