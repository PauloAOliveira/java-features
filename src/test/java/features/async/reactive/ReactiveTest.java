package features.async.reactive;

import com.jayway.awaitility.Awaitility;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class ReactiveTest {

    private Random random;

    @Before
    public void setup() {
        random = new Random();
    }

    @Test
    public void consumeWithOneSubscriber() {
        SubmissionPublisher publisher = new SubmissionPublisher();
        PrintSubscriber subscriber = new PrintSubscriber("Sub1");

        publisher.subscribe(subscriber);

        List<Long> toConsume = new ArrayList<>();
        Stream.iterate(1L, i -> i)
                .takeWhile(i -> toConsume.size() < 1000)
                .forEach(i -> toConsume.add(random.nextLong()));

        toConsume.forEach(value -> {
            System.out.println("Submit:" +value);
            publisher.submit(value);
        });

        Awaitility.await().atMost(10000, TimeUnit.MILLISECONDS)
                .until(() -> toConsume.size() == subscriber.getConsumed().size());
    }

    @Test
    public void consumeWithTwoSubscriber() {
        SubmissionPublisher publisher = new SubmissionPublisher();
        PrintSubscriber subscriber1 = new PrintSubscriber("Sub1");
        MultiplierSubscriber subscriber2 = new MultiplierSubscriber("Mult2", 5L);

        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);

        List<Long> toConsume = new ArrayList<>();
        Stream.iterate(1L, i -> i)
                .takeWhile(i -> toConsume.size() < 1000)
                .forEach(i -> toConsume.add(random.nextLong()));

        toConsume.forEach(value -> {
            System.out.println("Submit:" +value);
            publisher.submit(value);
        });

        Awaitility.await().atMost(10000, TimeUnit.MILLISECONDS)
                .until(() -> toConsume.size() == subscriber1.getConsumed().size() &&
                             toConsume.size() == subscriber2.getConsumed().size());
    }

}