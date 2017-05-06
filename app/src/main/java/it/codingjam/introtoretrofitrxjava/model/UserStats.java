package it.codingjam.introtoretrofitrxjava.model;

import com.google.auto.value.AutoValue;

import java.text.MessageFormat;
import java.util.List;

@AutoValue
public abstract class UserStats {

    public static UserStats create(User user, List<Tag> tags, List<Badge> badges) {
        return new AutoValue_UserStats(
                user,
                tags.size() > 5 ? tags.subList(0, 5) : tags,
                badges.size() > 5 ? badges.subList(0, 5) : badges
        );
    }

    public abstract User user();

    public abstract List<Tag> tags();

    public abstract List<Badge> badges();

    @Override public String toString() {
        return MessageFormat.format("{0}\n\t{1}\n\t{2}", user().toString(), listToString(tags()), listToString(badges()));
    }

    private String listToString(List<?> l) {
        String s = l.toString();
        return s.substring(1, s.length() - 1);
    }

    public static void printList(List<UserStats> statsList) {
        for (UserStats userStats : statsList) {
            System.out.println(userStats);
        }
    }
}
