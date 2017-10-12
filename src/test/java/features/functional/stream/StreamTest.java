package features.functional.stream;

import features.functional.domain.Genre;
import features.functional.domain.Person;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                new BigDecimal("1.11"),
                new BigDecimal("2.22"),
                new BigDecimal("3.33"),
                new BigDecimal("4.44"),
                new BigDecimal("5.55"),
                new BigDecimal("6.66"),
                new BigDecimal("7.77"));

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

    @Test
    public void shouldSumAllAges() {
        Optional<Integer> total = examples.sumAllAges();

        assertTrue(total.isPresent());
        assertEquals(Integer.valueOf(319), total.get());
    }

    @Test
    public void shouldSumAllValues() {
        Optional<BigDecimal> total = examples.sumAllValues();

        assertTrue(total.isPresent());
        assertEquals(new BigDecimal("31.08"), total.get());
    }

    @Test
    public void shouldSumAllValuesWithBaseOne() {
        BigDecimal total = examples.sumAllValuesWithBaseOne();

        assertEquals(new BigDecimal("32.08"), total);
    }

    @Test
    public void shouldConcatAllNames() {
        Optional<String> names = examples.concatAllPeopleNamesCase1(" ");
        String names2 = examples.concatAllPeopleNamesCase2(" ");

        String resp = "João Maria Maria Nelson Carlos Carla Fábio Rodrigo Rafael";
        assertTrue(names.isPresent());
        assertEquals(resp, names.get());
        assertEquals(resp, names2);
    }

    @Test
    public void shouldGroupByGenre() {
        Map<Genre, List<Person>> byGenre = examples.getPeopleGroupedByGenre();

        assertEquals(2, byGenre.keySet().size());
        assertEquals(6, byGenre.get(Genre.MALE).size());
        assertTrue(byGenre.get(Genre.MALE).containsAll(Arrays.asList(joao, nelson, carlos, fabio, rodrigo, rafael)));

        assertEquals(3, byGenre.get(Genre.FEMALE).size());
        assertTrue(byGenre.get(Genre.FEMALE).containsAll(Arrays.asList(maria1, maria2,carla)));
    }

    @Test
    public void shouldGroupNamesByGenre() {
        Map<Genre, List<String>> byGenre = examples.getPeopleNamesGroupedByGenre();

        assertEquals(2, byGenre.keySet().size());
        assertEquals(6, byGenre.get(Genre.MALE).size());
        assertTrue(byGenre.get(Genre.MALE).containsAll(Arrays.asList(joao.getName(), nelson.getName(), carlos.getName(),
                fabio.getName(), rodrigo.getName(), rafael.getName())));

        assertEquals(3, byGenre.get(Genre.FEMALE).size());
        assertTrue(byGenre.get(Genre.FEMALE).containsAll(Arrays.asList(maria1.getName(), maria2.getName(), carla.getName())));
    }

    @Test
    public void shouldConcatNamesByGenre() {
        Map<Genre, String> byGenre = examples.concatAllPeopleNamesByGenre(" ");

        assertEquals(2, byGenre.keySet().size());
        assertEquals("Maria Maria Carla", byGenre.get(Genre.FEMALE));
        assertEquals("João Nelson Carlos Fábio Rodrigo Rafael", byGenre.get(Genre.MALE));
    }

    @Test
    public void shouldReturnMaxAgeByGenre() {
        Map<Genre, Optional<Person>> maxAge = examples.getPeopleMaxAgeByGenre();

        assertEquals(2, maxAge.keySet().size());
        assertEquals(Integer.valueOf(67), maxAge.get(Genre.MALE).get().getAge());
        assertEquals(Integer.valueOf(35), maxAge.get(Genre.FEMALE).get().getAge());
    }

    @Test
    public void shouldReturnTotalByGenre() {
        Map<Genre, Long> totalByGenre = examples.getTotalOfPeopleByGenre();

        assertEquals(2, totalByGenre.keySet().size());
        assertEquals(Long.valueOf(6L), totalByGenre.get(Genre.MALE));
        assertEquals(Long.valueOf(3L), totalByGenre.get(Genre.FEMALE));
    }

    @Test
    public void shouldReturnDividedPeopleByAge() {
        Map<Boolean, List<Person>> dividedByAge = examples.dividePeopleWithAgeGreaterAndLessThan(25);

        assertEquals(2, dividedByAge.keySet().size());
        assertEquals(7, dividedByAge.get(true).size());
        assertEquals(2, dividedByAge.get(false).size());
    }

    @Test
    public void shouldReturnAllAgesWithMariaSummed() {
        Map<String, Integer> allAgesByName = examples.getAllAgesByName();

        assertEquals(8, allAgesByName.keySet().size());
        assertEquals(Integer.valueOf(31),allAgesByName.get("João"));
        assertEquals(Integer.valueOf(63),allAgesByName.get("Maria"));
        assertEquals(Integer.valueOf(14),allAgesByName.get("Nelson"));
        assertEquals(Integer.valueOf(67),allAgesByName.get("Carlos"));
        assertEquals(Integer.valueOf(26),allAgesByName.get("Carla"));
        assertEquals(Integer.valueOf(45),allAgesByName.get("Fábio"));
        assertEquals(Integer.valueOf(22),allAgesByName.get("Rodrigo"));
        assertEquals(Integer.valueOf(51),allAgesByName.get("Rafael"));
    }
}