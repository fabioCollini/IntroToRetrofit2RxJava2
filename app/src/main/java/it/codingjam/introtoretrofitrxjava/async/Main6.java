package it.codingjam.introtoretrofitrxjava.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import it.codingjam.introtoretrofitrxjava.StackOverflowServiceFactory;

public class Main6 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        StackOverflowService service = StackOverflowServiceFactory.createService(StackOverflowService.class);

        service.getTopUsers()
                .map(users -> users.size() > 5 ? users.subList(0, 5) : users)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(executor))
                .doFinally(executor::shutdown)
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace
                );

        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}
