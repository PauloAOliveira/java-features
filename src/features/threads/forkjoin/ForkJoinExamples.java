package features.threads.forkjoin;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.*;

/**
 * Based on http://www.baeldung.com/java-fork-join
 * */
public class ForkJoinExamples {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ForkJoinPool forkJoinPool1 = new ForkJoinPool(4);
        ConcurrentHashMap<Long, Long> alreadyExist = new ConcurrentHashMap<>();
        alreadyExist.put(1L, 1L);
        alreadyExist.put(2L, 1L);
        alreadyExist.put(3L, 2L);
        ForkJoinTask<Long> resp = forkJoinPool1.submit(new FibonacciTask(92L, alreadyExist));
        resp.join();

        System.out.println(Long.MAX_VALUE);
        System.out.println(resp.get());
    }

    private static class FibonacciTask extends RecursiveTask<Long> {

        private Long number;
        private ConcurrentMap<Long, Long> alreadyCalculated;

        private FibonacciTask(Long number, ConcurrentMap<Long, Long> alreadyCalculated) {
            this.number = number;
            this.alreadyCalculated = alreadyCalculated;
        }

        @Override
        protected Long compute() {
            System.out.println("Computing: "+number);
            Long calculated = alreadyCalculated.get(number);

            if(calculated == null) {
                System.out.println("Forking: "+number);
                calculated = ForkJoinTask.invokeAll(tasks()).stream()
                            .mapToLong(ForkJoinTask::join)
                            .sum();
                alreadyCalculated.put(number, calculated);
            }

            return calculated;
        }

        private Collection<FibonacciTask> tasks() {
            return Arrays.asList(new FibonacciTask(number-1, alreadyCalculated),
                    new FibonacciTask(number-2, alreadyCalculated));
        }
    }
}
