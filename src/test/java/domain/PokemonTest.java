package domain;

import domain.loader.Loader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PokemonTest {

    private static List<Pokemon> pokemons;

    @BeforeAll
    static void setup() throws IOException {
        pokemons = Loader.load();
    }

    @Test
    void shouldFilterPokemonsByType() {
        final var database = pokemons.subList(0, 10);
        final var pokemons = Pokemon.filterByType(database, Pokemon.Type.BUG);

        assertEquals(1, pokemons.size());
        assertEquals(pokemons.iterator().next().getName(), "Caterpie");
    }

    @Test
    void shouldGetAllCategories() {
        final var database = pokemons.subList(0, 6);
        final var categories = Pokemon.getAllCategories(database);

        assertEquals(categories, Set.of("Seed", "Lizard", "Flame"));
    }

    @Test
    void shouldBeWeakToElectric() {
        final var database = pokemons.subList(5, 9);
        assertTrue(Pokemon.isAllWeakTo(database, Pokemon.Type.ELECTRIC));
    }

    @Test
    void shouldNotBeWeakToElectric() {
        final var database = pokemons.subList(4, 9);
        assertFalse(Pokemon.isAllWeakTo(database, Pokemon.Type.ELECTRIC));
    }

    @Test
    void shouldSomeBeWeakToGround() {
        final var database = pokemons.subList(4, 9);
        assertTrue(Pokemon.isAnyWeakTo(database, Pokemon.Type.GROUND));
    }

    @Test
    void shouldNotSomeBeWeakToGround() {
        final var database = pokemons.subList(5, 10);
        assertFalse(Pokemon.isAnyWeakTo(database, Pokemon.Type.GROUND));
    }

    @Test
    void shouldGroupByCategory() {
        final var database = pokemons.subList(0, 6);
        final var pokemonsByCategory = Pokemon.groupByCategory(database);

        assertEquals(3, pokemonsByCategory.size());
        assertEquals(Set.of("Seed", "Lizard", "Flame"), pokemonsByCategory.keySet());
        assertEquals(3, pokemonsByCategory.get("Seed").size());
        assertEquals(1, pokemonsByCategory.get("Lizard").size());
        assertEquals(2, pokemonsByCategory.get("Flame").size());
    }

    @Test
    void shouldGroupNameByCategory() {
        final var database = pokemons.subList(0, 6);
        final var pokemonsByCategory = Pokemon.groupNameByCategory(database);

        assertEquals(3, pokemonsByCategory.size());
        assertEquals(Set.of("Seed", "Lizard", "Flame"), pokemonsByCategory.keySet());
        assertEquals(List.of("Bulbasaur", "Ivysaur", "Venusaur"), pokemonsByCategory.get("Seed"));
        assertEquals(List.of("Charmander"), pokemonsByCategory.get("Lizard"));
        assertEquals(List.of("Charmeleon", "Charizard"), pokemonsByCategory.get("Flame"));
    }

    @Test
    void shouldJoinNameByCategory() {
        final var database = pokemons.subList(0, 6);
        final var pokemonsByCategory = Pokemon.joinNamesByCategory(database);

        assertEquals(3, pokemonsByCategory.size());
        assertEquals(Set.of("Seed", "Lizard", "Flame"), pokemonsByCategory.keySet());
        assertEquals("Bulbasaur, Ivysaur, Venusaur", pokemonsByCategory.get("Seed"));
        assertEquals("Charmander", pokemonsByCategory.get("Lizard"));
        assertEquals("Charmeleon, Charizard", pokemonsByCategory.get("Flame"));
    }

    @Test
    void shouldReturnTallerByCategory() {
        final var database = pokemons.subList(0, 6);
        final var pokemonsByCategory = Pokemon.tallerByCategory(database);

        assertEquals(3, pokemonsByCategory.size());
        assertEquals(Set.of("Seed", "Lizard", "Flame"), pokemonsByCategory.keySet());
        assertEquals("Venusaur", pokemonsByCategory.get("Seed").orElseThrow().getName());
        assertEquals("Charmander", pokemonsByCategory.get("Lizard").orElseThrow().getName());
        assertEquals("Charizard", pokemonsByCategory.get("Flame").orElseThrow().getName());
    }

    @Test
    void shouldCountPerCategory() {
        final var database = pokemons.subList(0, 6);
        final var pokemonsByCategory = Pokemon.countPerCategory(database);

        assertEquals(3, pokemonsByCategory.size());
        assertEquals(Set.of("Seed", "Lizard", "Flame"), pokemonsByCategory.keySet());
        assertEquals(3, pokemonsByCategory.get("Seed"));
        assertEquals(1, pokemonsByCategory.get("Lizard"));
        assertEquals(2, pokemonsByCategory.get("Flame"));
    }

    @Test
    void shouldSplitByHeight() {
        final var database = pokemons.subList(0, 9);
        final var partitionByHeight = Pokemon.splitByHeightEqualsOrHigher(database, 1.0);

        assertEquals(3, partitionByHeight.get(false).size());
        assertEquals(6, partitionByHeight.get(true).size());
    }

    @Test
    void shouldMapWeightByName() {
        final var database = pokemons.subList(0, 3);
        final var weightByName = Pokemon.getWeightByName(database);

        assertEquals(3, weightByName.size());
        assertEquals(6.9, weightByName.get("Bulbasaur"));
        assertEquals(13, weightByName.get("Ivysaur"));
        assertEquals(100, weightByName.get("Venusaur"));
    }

    @Test
    void shouldSumWeight() {
        final var database = pokemons.subList(0, 3);
        final var sumWeight = Pokemon.sumWeight(database);

        assertTrue(sumWeight.isPresent());
        assertEquals(119.9, sumWeight.get());
    }

    @Test
    void shouldNotSumWeightWhenEmpty() {
        final var sumWeight = Pokemon.sumWeight(Collections.emptyList());

        assertTrue(sumWeight.isEmpty());
    }

    @Test
    void shouldSumWeightWithBase() {
        final var database = pokemons.subList(0, 3);
        final var sumWeight = Pokemon.sumWeightWithBase(database, 0);

        assertEquals(119.9, sumWeight);
    }

    @Test
    void shouldReturnDefaultWhenEmpty() {
        final var sumWeight = Pokemon.sumWeightWithBase(Collections.emptyList(), 0);

        assertEquals(0, sumWeight);
    }

    @Test
    void shouldConcatenateNames() {
        final var database = pokemons.subList(0, 3);
        final var names = Pokemon.concatenateNames(database, ", ");

        assertTrue(names.isPresent());
        assertEquals("Bulbasaur, Ivysaur, Venusaur", names.get());
    }

    @Test
    void shouldConcatenateCategories() {
        final var database = pokemons.subList(6, 9);
        final var categories = Pokemon.concatenateCategories(database, ", ");

        assertEquals("Tiny Turtle, Turtle, Shellfish", categories);
    }

    @Test
    void shouldReturnEmptyWhenConcatenateCategoriesWithEmptyList() {
        final var categories = Pokemon.concatenateCategories(Collections.emptyList(), ", ");

        assertEquals("", categories);
    }

    @Test
    void shouldDropAllUntilHeightIsGreaterThan() {
        final var database = pokemons.subList(0, 6);
        final var pokemons = Pokemon.dropUntilHasWeakness(database, Pokemon.Type.ELECTRIC);

        assertEquals(1, pokemons.size());
    }

    @Test
    void shouldTakeAllUntilHeightIsGreaterThan() {
        final var database = pokemons.subList(0, 15);
        final var pokemons = Pokemon.takeUntilHeightIsGreaterThan(database, 0.5);

        assertEquals(9, pokemons.size());
    }
}