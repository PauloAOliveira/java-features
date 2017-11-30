package features.collection.set;

import java.util.Set;

public class SetExamples {

    /**
     * Works only on Java 9
     *
     * Creates a immutable set
     * */
    public Set<Integer> factoryMethod() {
        return Set.of(1,2,3,4,5,6,7,8,9,10);
    }
}
