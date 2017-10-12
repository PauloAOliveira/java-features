package features.functional.optinal;

import features.functional.domain.City;
import features.functional.domain.Genre;
import features.functional.domain.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class OptionalTest {

    private Person person;
    private City city;
    private OptionalExamples examplesWithPerson;
    private OptionalExamples examplesWithoutPerson;

    @Before
    public void setup() {
        person = new Person("Carla", 15, Genre.FEMALE);
        city = new City("São Paulo");
        examplesWithPerson = new OptionalExamples(person);
        examplesWithoutPerson = new OptionalExamples();
    }

    @Test
    public void shouldSetCity() {
        examplesWithPerson.setCityIfPresent(city);

        Optional<Person> exPerson = examplesWithPerson.getPerson();

        assertTrue(exPerson.isPresent());
        assertTrue(exPerson.get().getCity().isPresent());
        assertEquals(city, exPerson.get().getCity().get());
    }

    @Test
    public void shouldNotSetCity() {
        examplesWithoutPerson.setCityIfPresent(city);

        Optional<Person> exPerson = examplesWithoutPerson.getPerson();

        assertFalse(exPerson.isPresent());
    }

    @Test
    public void shouldReturnFirstPerson() {
        Person otherPerson = new Person("Maria", 35, Genre.FEMALE);
        Person resp = examplesWithPerson.getIfAgeLessThanOrElse(20, otherPerson);

        assertEquals(person, resp);
    }

    @Test
    public void shouldReturnSecondPerson() {
        Person otherPerson = new Person("Maria", 35, Genre.FEMALE);
        Person resp = examplesWithPerson.getIfAgeLessThanOrElse(10, otherPerson);

        assertEquals(otherPerson, resp);
    }

    @Test
    public void shouldReturnName() {
        Optional<String> name = examplesWithPerson.getNameIfPresent();

        assertTrue(name.isPresent());
        assertEquals("Carla", name.get());
    }

    @Test
    public void shouldNotReturnName() {
        Optional<String> name = examplesWithoutPerson.getNameIfPresent();

        assertFalse(name.isPresent());
    }

    @Test
    public void shouldReturnCityName() {
        examplesWithPerson.setCityIfPresent(city);
        Optional<String> cityName = examplesWithPerson.getCityNameIfPresent();

        assertTrue(cityName.isPresent());
        assertEquals("São Paulo", cityName.get());
    }

    @Test
    public void shouldNotReturnCityName() {
        examplesWithoutPerson.setCityIfPresent(city);
        Optional<String> cityName = examplesWithoutPerson.getCityNameIfPresent();

        assertFalse(cityName.isPresent());
    }
}