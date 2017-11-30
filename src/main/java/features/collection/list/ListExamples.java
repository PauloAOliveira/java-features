package features.collection.list;

import java.util.List;

public class ListExamples {

    /**
     * Works only on Java 9
     *
     * Example of method to replace Arrays.asList(...)
     * Creates a immutable list
     * */
    public List<Integer> factoryMethod() {
        return List.of(1,2,3,4,5,6,7,8,9,10);
    }
}
