package features.async.completable.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableExamples {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws Exception {
        simpleAcceptCase();
        simpleExceptionCase();
        simpleHandleCase();
        simpleCombinedCase();
        simpleEitherCase();
    }

    private static void simpleAcceptCase() throws InterruptedException {
        System.out.println("simpleAcceptCase");
        CompletableFuture.supplyAsync(CompletableExamples::getUserId)
                .thenAccept(id -> System.out.println("Finished getting id = "+id));

        Thread.sleep(6000);
        System.out.println();
    }

    private static void simpleExceptionCase() throws InterruptedException {
        System.out.println("simpleExceptionCase");
        CompletableFuture.supplyAsync(() -> {
            waiting();
            throw new RuntimeException("Error");
        })
                .exceptionally(throwable -> {
                    System.out.println("Exception in async");
                    System.out.println(throwable.getMessage());
                    return -1;
                })
                .thenAccept(id -> System.out.println("Finished getting id = "+id));

        Thread.sleep(6000);
        System.out.println();
    }

    private static void simpleHandleCase() throws InterruptedException {
        System.out.println("simpleHandleCase");

        CompletableFuture.supplyAsync(() -> {
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

        Thread.sleep(6000);
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

    private static void simpleEitherCase() throws InterruptedException {
        System.out.println("simpleEitherCase");
        CompletableFuture<Integer> case1 = CompletableFuture.supplyAsync(() -> {
            waiting();
            return -1;
        });
        CompletableFuture<Integer> case2 = CompletableFuture.supplyAsync(() -> {
            waiting();
            return -2;
        });

        case1.acceptEither(case2, integer ->  {
            if(integer == -1) {
                System.out.println("Case 1 finished first");
            }else {
                System.out.println("Case 2 finished first");
            }
        });

        Thread.sleep(6000);
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

}
