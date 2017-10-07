package features.functional.optinal;

import features.functional.domain.City;
import features.functional.domain.Genre;
import features.functional.domain.Person;

import java.util.Optional;
import java.util.Random;

public class OptionalExamples {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        ifPresent();
        filterAndOrElse();
        map();
        flatMap();
    }

    private static void ifPresent() {
        System.out.println("If present do");
        randomPerson().ifPresent(System.out::println);
    }

    private static void filterAndOrElse() {
        System.out.println("Get people if greater than 40, otherwise get default people");
        Person resp = randomPerson().filter(person -> person.getAge() < 40)
                .orElse(new Person("José", 41, Genre.MALE));
        System.out.println(resp);
    }

    private static void map() {
        System.out.println("If person exists get name");
        randomPerson().map(Person::getName).ifPresent(System.out::println);
    }

    private static void flatMap() {
        System.out.println("Usage of flatmap");
        Person maria = new Person("Maria", 35, Genre.FEMALE);
        maria.setCity(new City("São Paulo"));
        Optional.of(maria)
                .flatMap(Person::getCity)
                .map(City::getName)
                .ifPresent(System.out::println);
    }

    private static Optional<Person> randomPerson() {
        if(RANDOM.nextInt() % 2 == 0) {
            return Optional.of(new Person("Maria", 35, Genre.FEMALE));
        } else {
            return Optional.empty();
        }
    }
}
