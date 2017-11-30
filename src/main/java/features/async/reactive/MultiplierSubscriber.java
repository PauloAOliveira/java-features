package features.async.reactive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

public class MultiplierSubscriber implements Flow.Subscriber<Long> {

    private static final Random RANDOM = new Random();
    private final String name;
    private final Long multiplier;
    private Subscription subscription;
    private List<Long> consumed = new ArrayList<>();

    public MultiplierSubscriber(String name, Long multiplier) {
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

    public List<Long> getConsumed() {
        return consumed;
    }
}
