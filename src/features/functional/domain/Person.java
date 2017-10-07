package features.functional.domain;

import java.util.Optional;

public class Person {
    private String name;
    private Integer age;
    private Genre genre;
    private City city;

    public Person(String name, Integer age, Genre genre) {
        this.name = name;
        this.age = age;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Genre getGenre() {
        return genre;
    }

    public Optional<City> getCity() {
        return Optional.ofNullable(city);
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", genre=" + genre +
                '}';
    }
}
