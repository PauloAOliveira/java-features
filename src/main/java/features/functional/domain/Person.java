package features.functional.domain;

import java.util.Objects;
import java.util.Optional;

public class Person {
    private String name;
    private Integer age;
    private Genre genre;
    private City city;

    public Person(String name, Integer age, Genre genre) {
        this.name = Objects.requireNonNull(name);
        this.age = Objects.requireNonNull(age);
        this.genre = Objects.requireNonNull(genre);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!name.equals(person.name)) return false;
        if (!age.equals(person.age)) return false;
        if (genre != person.genre) return false;
        return city != null ? city.equals(person.city) : person.city == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age.hashCode();
        result = 31 * result + genre.hashCode();
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
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
