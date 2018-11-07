package features.collection.list;

import java.util.List;

public class ListExamples {

    /**
     * Works only on Java 9
     *
     * Example of method to replace Arrays.asList(...)
     * Creates a immutable list
     * */
    public static List<Integer> factoryMethod() {
        return List.of(1,2,3,4,5,6,7,8,9,10);
    }

    /**
     * Works only on Java 10
     * */
    public static List<Integer> copyOf() {
        final var list = List.of(2, 4, 6);
        return List.copyOf(list);
    }
}
