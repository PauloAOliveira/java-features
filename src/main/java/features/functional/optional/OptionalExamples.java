package features.functional.optional;

import features.functional.domain.City;
import features.functional.domain.Genre;
import features.functional.domain.Person;

import java.util.Optional;

class OptionalExamples {

    private Optional<Person> person;

    OptionalExamples() {
        person = Optional.empty();
    }

    OptionalExamples(Person person) {
        this.person = Optional.ofNullable(person);
    }

    Optional<Person> getOptionalPerson() {
        return person;
    }

    Person getPerson() {
        return person.orElseThrow(() -> new IllegalStateException("There is no person"));
    }

    /**
     * Works only on Java 9
     * */
    Optional<Person> getPersonOrDefault() {
        return person.or(() -> Optional.of(getDefault()));
    }


    void setCityIfPresent(City city) {
        person.ifPresent(p -> p.setCity(city));
    }

    /**
     * Works only on Java 9
     * */
    void setCityIfPresentOrCreateDefault(City city) {
        person.ifPresentOrElse(p -> p.setCity(city), () -> {
            Person def = getDefault();
            def.setCity(city);
            person = Optional.of(def);
        });
    }

    Person getIfAgeLessThanOrElse(Integer age, Person other) {
        return person.filter(p -> p.getAge() < age).orElse(other);
    }

    Optional<String> getNameIfPresent() {
        return person.map(Person::getName);
    }

    Optional<String> getCityNameIfPresent() {
        return person.flatMap(Person::getCity).map(City::getName);
    }

    private Person getDefault() {
        return new Person("default", 1, Genre.FEMALE);
    }
}
