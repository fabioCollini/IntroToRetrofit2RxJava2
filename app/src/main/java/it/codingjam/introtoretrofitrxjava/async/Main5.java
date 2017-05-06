package it.codingjam.introtoretrofitrxjava.async;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class Main5 {
    public static void main(String[] args) {
        Observable.just(1, 2, 3);
        Observable.fromArray("A", "B", "C", "D");
        Observable.error(new IOException());
        Observable.interval(5, TimeUnit.SECONDS);
        Observable.fromCallable(() -> {
            //...
            System.out.println(123);
            return createFirstValue();
        });
        Observable.create(emitter -> {
            try {
                emitter.onNext(createFirstValue());
                emitter.onNext(createSecondValue());
                emitter.onComplete();
            } catch (Throwable t) {
                emitter.onError(t);
            }
        });

    }

    private static Object createSecondValue() {
        return null;
    }

    private static Object createFirstValue() {
        return null;
    }
}
