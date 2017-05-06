package it.codingjam.introtoretrofitrxjava.sync;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import it.codingjam.introtoretrofitrxjava.StackOverflowServiceFactory;
import it.codingjam.introtoretrofitrxjava.model.Badge;
import it.codingjam.introtoretrofitrxjava.model.Tag;
import it.codingjam.introtoretrofitrxjava.model.User;
import it.codingjam.introtoretrofitrxjava.model.UserStats;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        StackOverflowService service = StackOverflowServiceFactory.createService(StackOverflowService.class);


        MyCallback<UserStats> callback = new MyCallback<UserStats>() {
            @Override
            public void onResponse(UserStats response) {
                System.out.println(response);
                executor.shutdown();
            }

            @Override public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                executor.shutdown();
            }
        };

        enqueue(service.getTopUsers(), new MyCallback<List<User>>() {
            @Override
            public void onResponse(List<User> users) {
                User user = users.get(0);
                enqueue(service.getTags(user.id()), new MyCallback<List<Tag>>() {
                    @Override
                    public void onResponse(List<Tag> tags) {
                        enqueue(service.getBadges(user.id()), new MyCallback<List<Badge>>() {
                            @Override
                            public void onResponse(List<Badge> badges) {
                                callback.onResponse(UserStats.create(user, tags, badges));
                            }

                            @Override public void onFailure(Throwable throwable) {
                                callback.onFailure(throwable);
                            }
                        });
                    }

                    @Override public void onFailure(Throwable throwable) {
                        callback.onFailure(throwable);
                    }
                });
            }

            @Override public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
        });

        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    public interface MyCallback<T> {
        void onResponse(T obj);

        void onFailure(Throwable t);
    }

    private static <T> void enqueue(Call<T> call, MyCallback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(response.body());
                } else {
                    callback.onFailure(new IOException("Error code " + response.code()));
                }
            }

            @Override public void onFailure(Call<T> call, Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }
}
