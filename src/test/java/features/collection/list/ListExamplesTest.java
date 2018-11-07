package features.collection.list;

import org.junit.Test;

public class ListExamplesTest {

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowOnClear() {
        final var integers = ListExamples.copyOf();
        integers.clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowExceptionOnAdd() {
        final var integers = ListExamples.copyOf();
        integers.add(10);
    }
}