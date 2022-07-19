package domain;

import domain.Pokemon.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PokemonCreationTest {

    @Test
    void shouldCreatePokemon() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        final var pokemon = new Pokemon(name, number, height, weight, category, abilities, types, weaknesses, evolutions);

        assertEquals(name, pokemon.getName());
        assertEquals(number, pokemon.getNumber());
        assertEquals(height, pokemon.getHeight());
        assertEquals(weight, pokemon.getWeight());
        assertEquals(category, pokemon.getCategory());
        assertEquals(abilities, pokemon.getAbilities());
        assertEquals(types, pokemon.getTypes());
        assertEquals(weaknesses, pokemon.getWeaknesses());
        assertEquals(evolutions, pokemon.getEvolutions());
    }

    @Test
    void shouldNotCreatePokemonWhenNameIsBlank() {
        final var name = " ";
        final var number = 1;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenNameLengthIsGreaterThan12() {
        final var name = "1111111111111";
        final var number = 1;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenNumberIsLowerThanZero() {
        final var name = "Bulbasaur";
        final var number = -1;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenNumberIsGreaterThan905() {
        final var name = "Bulbasaur";
        final var number = 906;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenHeightIsLowerThanZero() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = -0.7;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenHeightIsGreaterThan20() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = 20.1;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenWeightIsLowerThanZero() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = 0.7;
        final var weight = -6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenWeightIsGreaterThan999() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = 0.7;
        final var weight = 1000;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenCategoryIsBlank() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = " ";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenCategoryLengthIsGreaterThan13() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = "11111111111111";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenAbilitiesIsGreaterThan3() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow", "Overgrow2", "Overgrow3", "Overgrow4");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities, types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenTypesIsEmpty() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.<Type>of();
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities, types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenTypesSizeIsGreaterThan7() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.ICE, Type.ICE, Type.ICE, Type.ICE, Type.ICE, Type.ICE, Type.ICE, Type.ICE);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities, types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenWeaknessesIsEmpty() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.<Type>of();
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenWeaknessesSizeIsGreaterThan7() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.GRASS, Type.POISON, Type.FIRE, Type.ICE, Type.BUG, Type.DARK, Type.DRAGON, Type.FIGHTING);
        final var evolutions = List.of("Ivysaur", "Venusaur");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }

    @Test
    void shouldNotCreatePokemonWhenEvolutionsIsGreaterThan9() {
        final var name = "Bulbasaur";
        final var number = 1;
        final var height = 0.7;
        final var weight = 6.9;
        final var category = "Seed";
        final var abilities = Set.of("Overgrow");
        final var types = List.of(Type.GRASS, Type.POISON);
        final var weaknesses = Set.of(Type.PSYCHIC, Type.FLYING, Type.FIRE, Type.ICE);
        final var evolutions = List.of("1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pokemon(name, number, height, weight, category, abilities,types, weaknesses, evolutions));
    }
}