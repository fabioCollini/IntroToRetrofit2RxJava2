package it.codingjam.introtoretrofitrxjava.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import it.codingjam.introtoretrofitrxjava.StackOverflowServiceFactory;

public class Main4 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        StackOverflowService service = StackOverflowServiceFactory.createService(StackOverflowService.class);

        service.getTopUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(executor))
                .subscribe(
                        users -> {
                            if (users.size() > 5) {
                                users = users.subList(0, 5);
                            }
                            System.out.println(users);
                            executor.shutdown();
                        },
                        t -> {
                            t.printStackTrace();
                            executor.shutdown();
                        }
                );

        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}
