package features.functional.stream;

import features.functional.domain.Genre;
import features.functional.domain.Person;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class StreamExamples {

    private List<Person> people;
    private List<BigDecimal> values;

    public StreamExamples(List<Person> people, List<BigDecimal> values) {
        this.people = people;
        this.values = values;
    }

    public List<Person> filterPeopleNameContains(String value) {
        return people.stream()
                .filter(person -> person.getName().contains(value))
                .collect(toList());
    }

    public List<Integer> getAllAges() {
        return people.stream()
                .map(Person::getAge)
                .collect(toList());
    }

    public boolean isAllPeopleWithAgeGreaterThan(Integer age) {
        return people.stream().allMatch(person -> person.getAge() > age);
    }

    public boolean isAnyPeopleWithAgeGreaterThan(Integer age) {
        return people.stream().anyMatch(person -> person.getAge() > age);
    }

    public Optional<Integer> sumAllAges() {
        return people.stream()
                .map(Person::getAge)
                .reduce((age1, age2) -> age1 + age2);
    }

    public Optional<BigDecimal> sumAllValues() {
        return values.stream().reduce(BigDecimal::add);
    }

    public BigDecimal sumAllValuesWithBaseOne() {
        return values.stream().reduce(BigDecimal.ONE, BigDecimal::add);
    }

    public Optional<String> concatAllPeopleNamesCase1() {
        return people.stream()
                .map(Person::getName)
                .reduce((name1, name2) -> name1.concat(" ").concat(name2));
    }

    public String concatAllPeopleNamesCase2() {
        return people.stream()
                .map(Person::getName)
                .collect(joining(" "));
    }

    public Map<Genre, List<Person>> getPeopleGroupedByGenre() {
        return people.stream().collect(groupingBy(Person::getGenre));
    }

    public Map<Genre, List<String>> getPeopleNamesGroupedByGenre() {
        return people.stream().collect(
                groupingBy(Person::getGenre,
                        mapping(Person::getName, toList())
                ));
    }

    public Map<Genre, String> concatAllPeopleNamesByGenre() {
        return people.stream().collect(
                groupingBy(Person::getGenre,
                        mapping(Person::getName, joining())
                ));
    }

    public Map<Genre, Optional<Person>> getPeopleMaxAgeByGenre() {
        return people.stream().collect(
                groupingBy(Person::getGenre,
                        maxBy(Comparator.comparing(Person::getAge))
                ));
    }

    public Map<Genre, Long> getTotalOfPeopleByGenre() {
        return people.stream().collect(
                groupingBy(Person::getGenre, counting()));
    }

    public Map<Boolean, List<Person>> dividePeopleWithAgeGreaterAndLessThan(Integer age) {
        return people.stream().collect(Collectors.partitioningBy(o -> o.getAge() > age));
    }

    public Map<String, Integer> getAllAgesByName() {
        return people.stream().collect(Collectors.toMap(
                Person::getName,
                Person::getAge,
                (p1, p2) -> p1 + p2));
    }
}
