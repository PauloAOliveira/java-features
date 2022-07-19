package domain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public final class Pokemon {

    public enum Type {
        WATER,
        FIGHTING,
        ROCK,
        GROUND,
        ELECTRIC,
        ICE,
        GRASS,
        NORMAL,
        STEEL,
        DRAGON,
        FLYING,
        PSYCHIC,
        BUG,
        DARK,
        GHOST,
        FIRE,
        POISON,
        FAIRY
    }

    private final String name;
    private final int number;
    private final double height;
    private final double weight;
    private final String category;
    private final Set<String> abilities;
    private final List<Type> types;
    private final Set<Type> weaknesses;
    private final List<String> evolutions;

    public Pokemon(final String name, final int number, final double height, final double weight, final String category, final Set<String> abilities, final List<Type> types, final Set<Type> weaknesses, final List<String> evolutions) {
        checkArgument(!name.isBlank());
        checkArgument(name.length() <= 12);
        this.name = name;
        checkArgument(number > 0);
        checkArgument(number <= 905);
        this.number = number;
        checkArgument(height >= 0.1);
        checkArgument(height <= 20.0);
        this.height = height;
        checkArgument(weight >= 0.1);
        checkArgument(weight < 1000);
        this.weight = weight;
        checkArgument(!category.isBlank());
        checkArgument(category.length() <= 13);
        this.category = category;
        checkArgument(abilities.size() <= 3);
        this.abilities = abilities;
        checkArgument(!types.isEmpty());
        checkArgument(types.size() <= 7);
        this.types = types;
        checkArgument(!weaknesses.isEmpty());
        checkArgument(weaknesses.size() <= 7);
        this.weaknesses = weaknesses;
        checkArgument(evolutions.size() <= 9);
        this.evolutions = evolutions;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public String getCategory() {
        return category;
    }

    public Set<String> getAbilities() {
        return abilities;
    }

    public List<Type> getTypes() {
        return types;
    }

    public Set<Type> getWeaknesses() {
        return weaknesses;
    }

    public List<String> getEvolutions() {
        return evolutions;
    }

    /**
     * Stream_Filter
     * */
    public static Set<Pokemon> filterByType(final List<Pokemon> pokemons, final Type typeToFilter) {
        return pokemons.stream()
                .filter(p -> p.getTypes().contains(typeToFilter))
                .collect(Collectors.toSet());
    }

    /**
     * Stream_Map
     * */
    public static Set<String> getAllCategories(final List<Pokemon> pokemons) {
        return pokemons.stream()
                .map(Pokemon::getCategory)
                .collect(Collectors.toSet());
    }

    /**
     * Stream_AllMatch
     * */
    public static boolean isAllWeakTo(final List<Pokemon> pokemons, final Type type) {
        return pokemons.stream().allMatch(p -> p.weaknesses.contains(type));
    }

    /**
     * Stream_AnyMatch
     * */
    public static boolean isAnyWeakTo(final List<Pokemon> pokemons, final Type type) {
        return pokemons.stream().anyMatch(p -> p.weaknesses.contains(type));
    }

    /**
     * Stream_GroupingBy
     */
    public static Map<String, List<Pokemon>> groupByCategory(final List<Pokemon> pokemons) {
        return pokemons.stream().collect(Collectors.groupingBy(Pokemon::getCategory));
    }

    /**
     * Stream_GroupingBy
     */
    public static Map<String, List<String>> groupNameByCategory(final List<Pokemon> pokemons) {
        return pokemons.stream().collect(Collectors.groupingBy(Pokemon::getCategory, mapping(Pokemon::getName, toList())));
    }

    /**
     * Stream_GroupingBy
     */
    public static Map<String, String> joinNamesByCategory(final List<Pokemon> pokemons) {
        return pokemons.stream().collect(Collectors.groupingBy(Pokemon::getCategory, mapping(Pokemon::getName, joining(", "))));
    }

    /**
     * Stream_GroupingBy
     */
    public static Map<String, Optional<Pokemon>> tallerByCategory(final List<Pokemon> pokemons) {
        return pokemons.stream().collect(groupingBy(Pokemon::getCategory, maxBy(Comparator.comparingDouble(Pokemon::getHeight))));
    }

    /**
     * Stream_GroupingBy
     */
    public static Map<String, Long> countPerCategory(final List<Pokemon> pokemons) {
        return pokemons.stream().collect(groupingBy(Pokemon::getCategory, counting()));
    }

    /**
     * Stream_PartitioningBy
     * */
    public static Map<Boolean, List<Pokemon>> splitByHeightEqualsOrHigher(final List<Pokemon> pokemons, final double height) {
        return pokemons.stream().collect(partitioningBy(p -> p.getHeight() >= height));
    }

    /**
     * Stream_ToMap
     * */
    public static Map<String, Double> getWeightByName(final List<Pokemon> pokemons) {
        return pokemons.stream().collect(toMap(Pokemon::getName, Pokemon::getWeight, (p1, p2) -> p1));
    }

    /**
     * Stream_Reduce_Number
     * */
    public static Optional<Double> sumWeight(final List<Pokemon> pokemons) {
        return pokemons.stream().map(Pokemon::getWeight).reduce(Double::sum);
    }

    /**
     * Stream_Reduce_Number
     * */
    public static Double sumWeightWithBase(final List<Pokemon> pokemons, final double base) {
        return pokemons.stream().map(Pokemon::getWeight).reduce(base, Double::sum);
    }

    /**
     * Stream_Reduce_Strings
     * */
    public static Optional<String> concatenateNames(final List<Pokemon> pokemons, final String delimiter) {
        return pokemons.stream().map(Pokemon::getName).reduce((n1, n2) -> n1.concat(delimiter).concat(n2));
    }

    /**
     * Stream_Joining
     * */
    public static String concatenateCategories(final List<Pokemon> pokemons, final String delimiter) {
        return pokemons.stream().map(Pokemon::getCategory).collect(joining(delimiter));
    }

    /**
     * Stream_DropWhile
     * */
    public static List<Pokemon> dropUntilHasWeakness(final List<Pokemon> pokemons, final Type weakness) {
        return pokemons.stream().dropWhile(p -> !p.getWeaknesses().contains(weakness)).collect(toList());
    }

    /**
     * Stream_TakeWhile
     * */
    public static List<Pokemon> takeUntilHeightIsGreaterThan(final List<Pokemon> pokemons, final Double height) {
        return pokemons.stream().takeWhile(p -> p.getHeight() >= height).collect(toList());
    }

    @Override
    public String toString() {
        return "domain.Pokemon{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", height=" + height +
                ", weight=" + weight +
                ", category='" + category + '\'' +
                ", abilities=" + abilities +
                ", types=" + types +
                ", weaknesses=" + weaknesses +
                ", evolutions=" + evolutions +
                '}';
    }
}
