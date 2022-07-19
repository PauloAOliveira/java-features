package domain.loader;

import domain.Pokemon;
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
import static com.google.common.base.Preconditions.checkArgument;

public class Loader {

    //Fixed amount of existing pokemon inside the "database"
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 905;

    public static List<Pokemon> load() throws IOException {
        return load(MIN_NUMBER, MAX_NUMBER);
    }

    /**
     * Max number of existing pokemon on MAX_NUMBER
     * @param begin - inclusive
     * @param end - inclusive
     * */
    public static List<Pokemon> load(final int begin, final int end) throws IOException {
        checkArgument(begin >= MIN_NUMBER);
        checkArgument(end <= MAX_NUMBER);
        checkArgument(begin < end);
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
                    .dropWhile(p -> p.getNumber() < begin)
                    .takeWhile(p -> p.getNumber() <= end)
                    .collect(Collectors.toList());
        }
    }

}
