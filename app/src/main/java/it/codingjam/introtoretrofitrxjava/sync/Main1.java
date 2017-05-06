package it.codingjam.introtoretrofitrxjava.sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.codingjam.introtoretrofitrxjava.StackOverflowServiceFactory;
import it.codingjam.introtoretrofitrxjava.model.Badge;
import it.codingjam.introtoretrofitrxjava.model.Tag;
import it.codingjam.introtoretrofitrxjava.model.User;
import it.codingjam.introtoretrofitrxjava.model.UserStats;

public class Main1 {
    public static void main(String[] args) throws IOException {
        StackOverflowService service = StackOverflowServiceFactory
                .createService(StackOverflowService.class);

        List<User> users = service.getTopUsers().execute().body();
        if (users.size() > 5) {
            users = users.subList(0, 5);
        }
        List<UserStats> statsList = new ArrayList<>();
        for (User user : users) {
            UserStats userStats = loadUserStats(service, user);
            statsList.add(userStats);
        }

        UserStats.printList(statsList);
    }

    private static UserStats loadUserStats(StackOverflowService service, User user) throws IOException {
        List<Tag> tags = service.getTags(user.id()).execute().body();
        List<Badge> badges = service.getBadges(user.id()).execute().body();

        return UserStats.create(user, tags, badges);
    }
}
