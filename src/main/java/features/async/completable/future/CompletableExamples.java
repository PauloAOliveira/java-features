package features.async.completable.future;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class CompletableExamples {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws Exception {
        simpleCompleteCase();
        simpleCompleteAsyncCase();
        simpleDelayCase();
        simpleAcceptCase();
        simpleExceptionCase();
        simpleHandleCase();
        simpleCombinedCase();
        simpleEitherCase();
        getAllOf();
    }

    private static void simpleCompleteCase() throws InterruptedException, TimeoutException, ExecutionException {
        System.out.println("simpleCompleteCase");
        CompletableFuture<Integer> future = new CompletableFuture<>();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            int userId = getUserId();
            future.complete(userId);
        });

        await(future, 6000);
        Integer resp = future.get();
        System.out.println("User: "+resp);
        executorService.shutdown();
        System.out.println();
    }

    /**
     * Works only on Java 9
     * */
    private static void simpleCompleteAsyncCase() throws InterruptedException, TimeoutException, ExecutionException {
        System.out.println("simpleCompleteAsynCase");
        CompletableFuture<Integer> future = new CompletableFuture<>();
        future.completeAsync(CompletableExamples::getUserId);

        await(future, 6000);
        Integer resp = future.get();
        System.out.println("User: "+resp);
        System.out.println();
    }

    /**
     * Works only on Java 9
     * */
    private static void simpleDelayCase() throws InterruptedException, TimeoutException, ExecutionException {
        System.out.println("simpleDelayCase");
        System.out.println(LocalDateTime.now());
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(LocalDateTime.now());
            waiting();
            return 1;
        }, CompletableFuture.delayedExecutor(10, TimeUnit.SECONDS))
                .thenAccept(id -> System.out.println("Finished with delay"));

        await(future, 16000);
        System.out.println();
    }

    private static void simpleAcceptCase() throws InterruptedException, TimeoutException, ExecutionException {
        System.out.println("simpleAcceptCase");
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(CompletableExamples::getUserId)
                .thenAccept(id -> System.out.println("Finished getting id = " + id));

        await(future, 6000);
        System.out.println();
    }

    private static void simpleExceptionCase() throws InterruptedException, TimeoutException, ExecutionException {
        System.out.println("simpleExceptionCase");
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            waiting();
            throw new RuntimeException("Error");
        }).exceptionally(throwable -> {
            System.out.println("Exception in async");
            System.out.println(throwable.getMessage());
            return -1;
        })
        .thenAccept(id -> System.out.println("Finished getting id = "+id));

        await(future, 6000);
        System.out.println();
    }

    private static void simpleHandleCase() throws InterruptedException, TimeoutException, ExecutionException {
        System.out.println("simpleHandleCase");

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            int userId = getUserId();
            if(userId % 2 == 0) {
                throw new RuntimeException("Even number");
            }
            return userId;
        }).handle((id, throwable) -> {

            if(throwable != null) {
                System.out.println("Exception in async");
                System.out.println(throwable.getMessage());
                return -1;
            }

            return id;
        }).thenAccept(id -> System.out.println("Finished getting id = "+id));

        await(future, 6000);
        System.out.println();
    }

    private static void simpleCombinedCase() throws InterruptedException, ExecutionException {
        System.out.println("simpleCombinedCase");
        CompletableFuture<Integer> c1 = CompletableFuture.supplyAsync(CompletableExamples::getUserId)
                .thenApply(id -> id + 1);

        CompletableFuture<Integer> c2 = CompletableFuture.supplyAsync(CompletableExamples::getOtherThing)
                .thenApply(id -> id + 2);

        CompletableFuture<Integer> c3 = c1.thenCombine(c2, (id1, id2) -> id1 + id2);

        System.out.println(c3.get());
        System.out.println();
    }

    private static void simpleEitherCase() throws InterruptedException, TimeoutException, ExecutionException {
        System.out.println("simpleEitherCase");
        CompletableFuture<Integer> case1 = CompletableFuture.supplyAsync(() -> {
            waiting();
            return -1;
        });
        CompletableFuture<Integer> case2 = CompletableFuture.supplyAsync(() -> {
            waiting();
            return -2;
        });

        CompletableFuture<Void> future = case1.acceptEither(case2, integer ->  {
            if(integer == -1) {
                System.out.println("Case 1 finished first");
            }else {
                System.out.println("Case 2 finished first");
            }
        });

        await(future, 6000);
        System.out.println();
    }

    private static void getAllOf() {
        System.out.println("getAllOf");
        CompletableFuture[] futureIds = Stream.generate(() -> CompletableFuture.supplyAsync(CompletableExamples::getUserId))
                .limit(20)
                .toArray(CompletableFuture[]::new);

        System.out.println("doing join");
        CompletableFuture.allOf(futureIds).join();
        System.out.println("join done");

        Arrays.stream(futureIds)
                .map(CompletableFuture::join)
                .forEach(System.out::println);
        System.out.println();
    }

    private static int getUserId() {
        System.out.println("Long operation to take id");
        waiting();
        int id = getInt();
        System.out.println("Took id = "+id);
        return id;
    }

    private static int getOtherThing() {
        System.out.println("Long operation to take other thing");
        waiting();
        int otherThing = getInt();
        System.out.println("Took other thing = "+otherThing);
        return otherThing;
    }

    private static int getInt() {
        return RANDOM.nextInt(100);
    }

    private static void waiting() {
        try {
            Thread.sleep((new Random()).nextInt(5)*1000);
        } catch (InterruptedException ignored) {

        }
    }

    private static <T> void await(Future<T> future, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        future.get(timeout, TimeUnit.MILLISECONDS);
    }
}
