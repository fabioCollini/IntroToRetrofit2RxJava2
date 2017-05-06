package it.codingjam.introtoretrofitrxjava.async;

import it.codingjam.introtoretrofitrxjava.StackOverflowServiceFactory;

public class Main3 {
    public static void main(String[] args) throws InterruptedException {
        StackOverflowService service = StackOverflowServiceFactory.createService(StackOverflowService.class);

        service.getTopUsers()
                .subscribe(
                        users -> {
                            if (users.size() > 5) {
                                users = users.subList(0, 5);
                            }
                            System.out.println(users);
                        },
                        Throwable::printStackTrace
                );
    }
}
