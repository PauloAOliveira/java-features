package br.com.java.features.repository;

import br.com.java.features.domain.Pokemon;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PokemonRepository {

    private static final List<Pokemon> pokemons;

    static {
        try {
            pokemons = load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Pokemon> findAll() {
        return pokemons;
    }

    private static List<Pokemon> load() throws IOException {
        try(final Stream<String> pokemons = Files.lines(Path.of("src", "test", "resources", "pokemons.csv"))) {
            return pokemons
                    .map(pokemon -> {
                        final var data = pokemon.split(",");
                        final var name = data[0];
                        final var number = Integer.parseInt(data[1]);
                        final var height = Double.parseDouble(data[2]);
                        final var weight = Double.parseDouble(data[3]);
                        final var category = data[4];
                        final Set<String> abilities;
                        if(data[5].isBlank()) {
                            abilities = Collections.emptySet();
                        } else {
                            abilities = new HashSet<>(Arrays.asList(data[5].split(";")));
                        }
                        final var types = Arrays.stream(data[6].split(";")).map(Pokemon.Type::valueOf).collect(Collectors.toList());
                        final var weaknesses = Arrays.stream(data[7].split(";")).map(Pokemon.Type::valueOf).collect(Collectors.toSet());
                        final List<String> evolutions;
                        if(data.length < 9) {
                            evolutions = Collections.emptyList();
                        } else {
                            evolutions = Arrays.asList(data[8].split(";"));
                        }
                        return new Pokemon(name, number, height, weight, category, abilities, types, weaknesses, evolutions);
                    })
                    .collect(Collectors.toList());
        }
    }
}
