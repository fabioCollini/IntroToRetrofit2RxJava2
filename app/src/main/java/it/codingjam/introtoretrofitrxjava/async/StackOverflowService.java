package it.codingjam.introtoretrofitrxjava.async;

import java.util.List;

import io.reactivex.Single;
import it.codingjam.introtoretrofitrxjava.model.Badge;
import it.codingjam.introtoretrofitrxjava.model.Tag;
import it.codingjam.introtoretrofitrxjava.model.User;
import it.codingjam.introtoretrofitrxjava.utils.EnvelopePayload;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StackOverflowService {

    @EnvelopePayload("items")
    @GET("/users") Single<List<User>> getTopUsers();

    @EnvelopePayload("items")
    @GET("/users/{userId}/top-tags") Single<List<Tag>> getTags(@Path("userId") int userId);

    @EnvelopePayload("items")
    @GET("/users/{userId}/badges") Single<List<Badge>> getBadges(@Path("userId") int userId);
}
