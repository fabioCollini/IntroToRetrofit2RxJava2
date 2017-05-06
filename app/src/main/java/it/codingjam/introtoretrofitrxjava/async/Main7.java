package it.codingjam.introtoretrofitrxjava.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import it.codingjam.introtoretrofitrxjava.StackOverflowServiceFactory;
import it.codingjam.introtoretrofitrxjava.model.UserStats;

public class Main7 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        StackOverflowService service = StackOverflowServiceFactory.createService(StackOverflowService.class);

        service.getTopUsers()
                .flattenAsObservable(users -> users)
                .take(5)
                .concatMapEager(user -> Single.zip(
                        service.getBadges(user.id()).subscribeOn(Schedulers.io()),
                        service.getTags(user.id()).subscribeOn(Schedulers.io()),
                        (badges, tags) -> UserStats.create(user, tags, badges)
                ).toObservable())
                .toList()
                .doFinally(executor::shutdown)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(executor))
                .subscribe(
                        UserStats::printList,
                        Throwable::printStackTrace
                );

        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

}
