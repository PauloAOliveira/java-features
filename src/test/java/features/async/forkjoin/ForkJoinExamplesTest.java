package features.async.forkjoin;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ForkJoinExamplesTest {

    private ForkJoinExamples forkJoinExamples;

    @BeforeEach
    public void setup() {
        forkJoinExamples = new ForkJoinExamples();
    }

    @Test
    @Timeout(value = 250, unit = TimeUnit.MILLISECONDS)
    public void shouldReturnCorrectValue() throws ExecutionException, InterruptedException {
        Long fibonacci = forkJoinExamples.getFibonacciOf(10L);
        assertEquals(Long.valueOf(55L), fibonacci);
    }

    @Test
    @Timeout(value = 250, unit = TimeUnit.MILLISECONDS)
    public void shouldReturnCorrectValues() throws ExecutionException, InterruptedException {

        Long fibonacci = forkJoinExamples.getFibonacciOf(10L);
        assertEquals(Long.valueOf(55L), fibonacci);

        fibonacci = forkJoinExamples.getFibonacciOf(9L);
        assertEquals(Long.valueOf(34L), fibonacci);

        fibonacci = forkJoinExamples.getFibonacciOf(8L);
        assertEquals(Long.valueOf(21L), fibonacci);
    }
}
