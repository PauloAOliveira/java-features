package features.functional.stream;

import features.functional.domain.Genre;
import features.functional.domain.Person;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StreamTest {

    private Person joao;
    private Person maria1;
    private Person maria2;
    private Person nelson;
    private Person carlos;
    private Person carla;
    private Person fabio;
    private Person rodrigo;
    private Person rafael;
    private StreamExamples examples;

    @Before
    public void setup() {
        joao = new Person("João", 31, Genre.MALE);
        maria1 = new Person("Maria", 28, Genre.FEMALE);
        maria2 = new Person("Maria", 35, Genre.FEMALE);
        nelson = new Person("Nelson", 14, Genre.MALE);
        carlos = new Person("Carlos", 67, Genre.MALE);
        carla = new Person("Carla", 26, Genre.FEMALE);
        fabio = new Person("Fábio", 45, Genre.MALE);
        rodrigo = new Person("Rodrigo", 22, Genre.MALE);
        rafael = new Person("Rafael", 51, Genre.MALE);
        List<Person> people = Arrays.asList(
                joao, maria1, maria2, nelson,
                carlos, carla, fabio, rodrigo, rafael);

        List<BigDecimal> values = Arrays.asList(
                new BigDecimal("1"),
                new BigDecimal("2"),
                new BigDecimal("3"),
                new BigDecimal("4"),
                new BigDecimal("5"),
                new BigDecimal("6"),
                new BigDecimal("7"));

        examples = new StreamExamples(people, values);
    }

    @Test
    public void shouldFilterByAr() {
        List<Person> resp = examples.filterPeopleNameContains("ar");

        assertEquals(4, resp.size());
        assertTrue(resp.contains(maria1));
        assertTrue(resp.contains(maria2));
        assertTrue(resp.contains(carlos));
        assertTrue(resp.contains(carla));
    }

    @Test
    public void shouldReturnAllAges() {
        List<Integer> allAges = examples.getAllAges();

        assertEquals(9, allAges.size());
        assertTrue(allAges.containsAll(Arrays.asList(joao.getAge(),
                maria1.getAge(), maria2.getAge(),
                nelson.getAge(), carlos.getAge(),
                carla.getAge(), fabio.getAge(),
                rodrigo.getAge(), rafael.getAge())));
    }

    @Test
    public void shouldReturnTrueAllAgesGreaterThan12() {
        assertTrue(examples.isAllPeopleWithAgeGreaterThan(12));
    }

    @Test
    public void shouldReturnFalseAllAgesGreaterThan15() {
        assertFalse(examples.isAllPeopleWithAgeGreaterThan(15));
    }

    @Test
    public void shouldReturnTrueAnyAgeGreaterThan22() {
        assertTrue(examples.isAnyPeopleWithAgeGreaterThan(22));
    }

    @Test
    public void shouldReturnFalseAnyAgeGreaterThan24() {
        assertTrue(examples.isAnyPeopleWithAgeGreaterThan(24));
    }
}