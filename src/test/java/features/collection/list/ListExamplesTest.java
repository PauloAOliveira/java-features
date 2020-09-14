package features.collection.list;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListExamplesTest {

    @Test
    public void shouldThrowOnClear() {
        final var integers = ListExamples.copyOf();
        Assertions.assertThrows(UnsupportedOperationException.class, integers::clear);
    }

    @Test
    public void shouldThrowExceptionOnAdd() {
        final var integers = ListExamples.copyOf();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> integers.add(10));
    }
}
