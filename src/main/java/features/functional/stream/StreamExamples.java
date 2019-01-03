package features.functional.stream;

import features.functional.domain.Genre;
import features.functional.domain.Person;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

class StreamExamples {

    private List<Person> people;
    private List<BigDecimal> values;

    StreamExamples(List<Person> people, List<BigDecimal> values) {
        this.people = people;
        this.values = values;
    }

    /**
     * Index based loop without for
     *
     * Works only on Java 9
     * */
    static List<String> getListOfNumbersUntil(int max) {
        return Stream.iterate(1, i -> i < max, i-> ++i)
                .map(Object::toString)
                .collect(toList());
    }

    List<Person> filterPeopleNameContains(String value) {
        return people.stream()
                .filter(person -> person.getName().contains(value))
                .collect(toList());
    }

    List<Integer> getAllAges() {
        return people.stream()
                .map(Person::getAge)
                .collect(toList());
    }

    boolean isAllPeopleWithAgeGreaterThan(Integer age) {
        return people.stream().allMatch(person -> person.getAge() > age);
    }

    boolean isAnyPeopleWithAgeGreaterThan(Integer age) {
        return people.stream().anyMatch(person -> person.getAge() > age);
    }

    Optional<Integer> sumAllAges() {
        return people.stream()
                .map(Person::getAge)
                .reduce((age1, age2) -> age1 + age2);
    }

    Optional<BigDecimal> sumAllValues() {
        return values.stream().reduce(BigDecimal::add);
    }

    BigDecimal sumAllValuesWithBaseOne() {
        return values.stream().reduce(BigDecimal.ONE, BigDecimal::add);
    }

    Optional<String> concatAllPeopleNamesCase1(String delimiter) {
        return people.stream()
                .map(Person::getName)
                .reduce((name1, name2) -> name1.concat(delimiter).concat(name2));
    }

    String concatAllPeopleNamesCase2(String delimiter) {
        return people.stream()
                .map(Person::getName)
                .collect(joining(delimiter));
    }

    Map<Genre, List<Person>> getPeopleGroupedByGenre() {
        return people.stream().collect(groupingBy(Person::getGenre));
    }

    Map<Genre, List<String>> getPeopleNamesGroupedByGenre() {
        return people.stream().collect(
                groupingBy(Person::getGenre,
                        mapping(Person::getName, toList())
                ));
    }

    Map<Genre, String> concatAllPeopleNamesByGenre(String delimiter) {
        return people.stream().collect(
                groupingBy(Person::getGenre,
                        mapping(Person::getName, joining(delimiter))
                ));
    }

    Map<Genre, Optional<Person>> getPeopleMaxAgeByGenre() {
        return people.stream().collect(
                groupingBy(Person::getGenre,
                        maxBy(Comparator.comparing(Person::getAge))
                ));
    }

    Map<Genre, Long> getTotalOfPeopleByGenre() {
        return people.stream().collect(
                groupingBy(Person::getGenre, counting()));
    }

    Map<Boolean, List<Person>> dividePeopleWithAgeGreaterAndLessThan(Integer age) {
        return people.stream().collect(Collectors.partitioningBy(o -> o.getAge() > age));
    }

    Map<String, Integer> getAllAgesByName() {
        return people.stream().collect(Collectors.toMap(
                Person::getName,
                Person::getAge,
                (p1, p2) -> p1 + p2));
    }

    /**
     * Works only on Java 9
     * */
    List<Person> removeUntilAgeGreaterThan(Integer age){
        return people.stream()
                .dropWhile(person -> person.getAge().compareTo(age) <= 0)
                .collect(toList());
    }

    /**
     * Works only on Java 9
     * */
    List<Person> returnUntilAgeLessThan(Integer age) {
        return people.stream()
                .takeWhile(person -> person.getAge().compareTo(age) <= 0)
                .collect(toList());
    }
}
