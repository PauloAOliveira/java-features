package features.functional.optional;

import features.functional.domain.City;
import features.functional.domain.Genre;
import features.functional.domain.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class OptionalExamplesTest {

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

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException() {
        examplesWithoutPerson.getPerson();
    }

    @Test
    public void shouldSetCity() {
        examplesWithPerson.setCityIfPresent(city);

        Optional<Person> exPerson = examplesWithPerson.getOptionalPerson();

        assertTrue(exPerson.isPresent());
        assertTrue(exPerson.get().getCity().isPresent());
        assertEquals(city, exPerson.get().getCity().get());
    }

    @Test
    public void shouldNotSetCity() {
        examplesWithoutPerson.setCityIfPresent(city);

        Optional<Person> exPerson = examplesWithoutPerson.getOptionalPerson();

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

    @Test
    public void shouldNotGetDefault() {
        Optional<Person> response = examplesWithPerson.getPersonOrDefault();

        assertTrue(response.isPresent());
        assertEquals(person, response.get());
    }

    @Test
    public void shouldGetDefault() {
        Optional<Person> response = examplesWithoutPerson.getPersonOrDefault();

        assertTrue(response.isPresent());
        assertEquals("default", response.get().getName());
        assertEquals(Integer.valueOf(1), response.get().getAge());
        assertEquals(Genre.FEMALE, response.get().getGenre());
    }

    @Test
    public void shouldSetCityOnDefault() {
        OptionalExamples examples = new OptionalExamples();
        Optional<Person> person = examples.getOptionalPerson();

        assertFalse(person.isPresent());

        Person def = new Person("default", 1, Genre.FEMALE);
        City city = new City("City");
        def.setCity(city);
        examples.setCityIfPresentOrCreateDefault(city);

        assertTrue(examples.getOptionalPerson().isPresent());
        assertEquals(def, examples.getOptionalPerson().get());
    }
}