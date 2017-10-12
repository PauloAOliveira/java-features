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

    @Before
    public void setup() {
        person = new Person("Carla", 15, Genre.FEMALE);
        city = new City("São Paulo");
    }

    @Test
    public void shouldSetCity() {
        OptionalExamples examples = new OptionalExamples(person);

        examples.setCityIfPresent(city);

        Optional<Person> exPerson = examples.getPerson();

        assertTrue(exPerson.isPresent());
        assertTrue(exPerson.get().getCity().isPresent());
        assertEquals(city, exPerson.get().getCity().get());
    }

    @Test
    public void shouldNotSetCity() {
        OptionalExamples examples = new OptionalExamples(null);

        examples.setCityIfPresent(city);

        Optional<Person> exPerson = examples.getPerson();

        assertFalse(exPerson.isPresent());
    }

    @Test
    public void shouldReturnFirstPerson() {
        OptionalExamples examples = new OptionalExamples(person);

        Person otherPerson = new Person("Maria", 35, Genre.FEMALE);
        Person resp = examples.getIfAgeLessThanOrElse(20, otherPerson);

        assertEquals(person, resp);
    }

    @Test
    public void shouldReturnSecondPerson() {
        OptionalExamples examples = new OptionalExamples(person);

        Person otherPerson = new Person("Maria", 35, Genre.FEMALE);
        Person resp = examples.getIfAgeLessThanOrElse(10, otherPerson);

        assertEquals(otherPerson, resp);
    }

    @Test
    public void shouldReturnName() {
        OptionalExamples examples = new OptionalExamples(person);

        Optional<String> name = examples.getNameIfPresent();

        assertTrue(name.isPresent());
        assertEquals("Carla", name.get());
    }

    @Test
    public void shouldNotReturnName() {
        OptionalExamples examples = new OptionalExamples(null);

        Optional<String> name = examples.getNameIfPresent();

        assertFalse(name.isPresent());
    }

    @Test
    public void shouldReturnCityName() {
        Person otherPerson = new Person("Maria", 35, Genre.FEMALE);
        OptionalExamples examples = new OptionalExamples(otherPerson);

        examples.setCityIfPresent(city);
        Optional<String> cityName = examples.getCityNameIfPresent();

        assertTrue(cityName.isPresent());
        assertEquals("São Paulo", cityName.get());
    }

    @Test
    public void shouldNotReturnCityName() {
        OptionalExamples examples = new OptionalExamples(null);

        examples.setCityIfPresent(city);
        Optional<String> cityName = examples.getCityNameIfPresent();

        assertFalse(cityName.isPresent());
    }
}