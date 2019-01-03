package features.async.reactive;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

/**
 * Works only on Java 9
 * */
public class MultiplierSubscriber implements Flow.Subscriber<Long> {

    private final String name;
    private final Long multiplier;
    private Subscription subscription;
    private List<Long> consumed = new ArrayList<>();

    MultiplierSubscriber(String name, Long multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Long item) {
        Long itemMultiplied = item * multiplier;
        consumed.add(itemMultiplied);
        System.out.println(name + " - " + itemMultiplied);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Complete");
    }

    List<Long> getConsumed() {
        return consumed;
    }
}
