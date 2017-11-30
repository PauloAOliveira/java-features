package features.collection.map;

import java.util.Map;

import static java.util.Map.entry;

public class MapExamples {

    /**
     * Works only on Java 9
     *
     * Creates a immutable map, case 1
     * */
    public Map<String, Integer> factoryMethod() {
        return Map.of(
                "k1",1,
                "k2",2,
                "k3",3,
                "k4",4,
                "k5",5,
                "k6",6,
                "k7",7,
                "k8",8,
                "k9",9,
                "k10",10
        );
    }

    /**
     * Works only on Java 9
     *
     * Creates a immutable map, case 2, by entries
     * */
    public Map<String, Integer> factoryMethodByEntries() {
        return Map.ofEntries(
                entry("k1", 1),
                entry("k2", 2),
                entry("k3", 3),
                entry("k4", 4),
                entry("k5", 5),
                entry("k6", 6)
        );
    }
}
