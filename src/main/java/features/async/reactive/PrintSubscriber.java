package features.async.reactive;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

/**
 * Works only on Java 9
 * */
public class PrintSubscriber implements Flow.Subscriber<Long> {

    private final String name;
    private Subscription subscription;
    private List<Long> consumed = new ArrayList<>();

    public PrintSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Long item) {
        consumed.add(item);
        System.out.println(name + " - " + item);
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
