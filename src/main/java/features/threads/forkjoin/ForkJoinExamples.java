package features.threads.forkjoin;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.*;

/**
 * Based on http://www.baeldung.com/java-fork-join
 * */
public class ForkJoinExamples {

    private ConcurrentMap<Long, Long> alreadyExist;
    private ForkJoinPool forkJoinPool;

    public ForkJoinExamples() {
        forkJoinPool = new ForkJoinPool(3);

        alreadyExist = new ConcurrentHashMap<>();
        alreadyExist.put(1L, 1L);
        alreadyExist.put(2L, 1L);
        alreadyExist.put(3L, 2L);
    }

    public Long getFibonacciOf(Long num) throws ExecutionException, InterruptedException {
        Long value = alreadyExist.get(num);

        if(value != null)
            return value;

        ForkJoinTask<Long> resp = forkJoinPool.submit(new FibonacciTask(num, alreadyExist));
        resp.join();

        return resp.get();
    }

    private static class FibonacciTask extends RecursiveTask<Long> {

        private static final long serialVersionUID = -1862697355522801437L;
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
