package features.async.forkjoin;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class ForkJoinExamplesTest {

    private ForkJoinExamples forkJoinExamples;

    @Before
    public void setup() {
        forkJoinExamples = new ForkJoinExamples();
    }

    @Test(timeout = 250L)
    public void shouldReturnCorrectValue() throws ExecutionException, InterruptedException {
        Long fibonacci = forkJoinExamples.getFibonacciOf(10L);
        assertEquals(Long.valueOf(55L), fibonacci);
    }

    @Test(timeout = 250L)
    public void shouldReturnCorrectValues() throws ExecutionException, InterruptedException {

        Long fibonacci = forkJoinExamples.getFibonacciOf(10L);
        assertEquals(Long.valueOf(55L), fibonacci);

        fibonacci = forkJoinExamples.getFibonacciOf(9L);
        assertEquals(Long.valueOf(34L), fibonacci);

        fibonacci = forkJoinExamples.getFibonacciOf(8L);
        assertEquals(Long.valueOf(21L), fibonacci);
    }
}