package features.functional.stream;

import features.functional.domain.Genre;
import features.functional.domain.Person;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class StreamExamples {

    private static List<Person> PEOPLE = Arrays.asList(
            new Person("João", 31, Genre.MALE),
            new Person("Maria", 28, Genre.FEMALE),
            new Person("Maria", 35, Genre.FEMALE),
            new Person("Nelson", 14, Genre.MALE),
            new Person("Carlos", 67, Genre.MALE),
            new Person("Carla", 26, Genre.FEMALE),
            new Person("Fábio", 45, Genre.MALE),
            new Person("Rodrigo", 22, Genre.MALE),
            new Person("Rafael", 51, Genre.MALE));

    private static List<BigDecimal> DECIMALS = Arrays.asList(
            new BigDecimal("1"),
            new BigDecimal("2"),
            new BigDecimal("3"),
            new BigDecimal("4"),
            new BigDecimal("5"),
            new BigDecimal("6"),
            new BigDecimal("7"));

    public static void main(String[] args) {
        simpleFilter();
        simpleMap();
        simpleMatch();
        reduce();
        reduceCount();
        reduceJoin();
        groupBy();
        partitioningBy();
        toMap();
    }

    private static void simpleFilter() {
        System.out.println("Filter fruits that contains 'i'");
        List<String> fruits = Arrays.asList(
                "Apple", "Avocado",
                "Banana", "Cherries",
                "Figs");

        List<String> filtered = fruits.stream()
                .filter(fruit -> fruit.contains("i"))
                .collect(toList());

        System.out.println(filtered);
        System.out.println();
    }

    private static void simpleMap() {
        System.out.println("Get the age of all people");

        List<Integer> ages = PEOPLE.stream()
                .map(Person::getAge)
                .collect(toList());

        System.out.println(ages);
        System.out.println();
    }

    private static void simpleMatch() {
        System.out.println("Verifying if all people has more than 10 years");
        boolean resp = PEOPLE.stream().allMatch(person -> person.getAge() > 10);
        System.out.println(resp);

        System.out.println("Verifying if any people has more than 62 years");
        resp = PEOPLE.stream().anyMatch(person -> person.getAge() > 62);
        System.out.println(resp);
        System.out.println();
    }

    private static void reduceCount() {
        System.out.println("Get ages and sum all of them");
        PEOPLE.stream()
                .map(Person::getAge)
                .reduce((age1, age2) -> age1 + age2)
                .ifPresent(System.out::println);
        System.out.println();
    }

    private static void reduce() {
        System.out.println("Sum all decimals");
        Optional<BigDecimal> sum = DECIMALS.stream().reduce(BigDecimal::add);
        sum.ifPresent(System.out::println);

        System.out.println("Sum all decimals with base one");
        BigDecimal reduced = DECIMALS.stream().reduce(BigDecimal.ONE, BigDecimal::add);
        System.out.println(reduced);
        System.out.println();
    }

    private static void reduceJoin() {
        System.out.println("Get all names and join them");
        PEOPLE.stream()
                .map(Person::getName)
                .reduce((name1, name2) -> name1.concat(" ").concat(name2))
                .ifPresent(System.out::println);
        //or
        String resp = PEOPLE.stream()
                .map(Person::getName)
                .collect(joining(" "));
        System.out.println(resp);
        System.out.println();
    }

    private static void groupBy() {
        System.out.println("Group people by genre");
        Map<Genre, List<Person>> byGenre = PEOPLE.stream()
                                                .collect(groupingBy(Person::getGenre));
        System.out.println(byGenre);

        Map<Genre, String> byGenreWithNameConcat = PEOPLE.stream().collect(
                groupingBy(Person::getGenre,
                        mapping(Person::getName, joining())
                ));
        System.out.println(byGenreWithNameConcat);

        Map<Genre, Optional<Person>> byGenreWithMaxAge = PEOPLE.stream().collect(
                groupingBy(Person::getGenre,
                        maxBy(Comparator.comparing(Person::getAge))
                ));
        System.out.println(byGenreWithMaxAge);
        System.out.println();
    }

    private static void partitioningBy() {
        System.out.println("Partitioning by age greater than 20");
        Map<Boolean, List<Person>> partition = PEOPLE.stream().collect(Collectors.partitioningBy(o -> o.getAge() > 20));
        System.out.println(partition);
        System.out.println();
    }

    private static void toMap() {
        System.out.println("Map name with age, if there is a repeat name sum the ages");
        Map<String, Integer> map = PEOPLE.stream().collect(Collectors.toMap(
                Person::getName,
                Person::getAge,
                (p1, p2) -> p1 + p2));
        System.out.println(map);
        System.out.println();
    }
}
