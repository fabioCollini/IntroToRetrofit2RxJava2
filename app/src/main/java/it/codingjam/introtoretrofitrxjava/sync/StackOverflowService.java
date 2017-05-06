package it.codingjam.introtoretrofitrxjava.sync;

import java.util.List;

import it.codingjam.introtoretrofitrxjava.model.Badge;
import it.codingjam.introtoretrofitrxjava.model.Tag;
import it.codingjam.introtoretrofitrxjava.model.User;
import it.codingjam.introtoretrofitrxjava.utils.EnvelopePayload;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StackOverflowService {

    @EnvelopePayload("items")
    @GET("/users") Call<List<User>> getTopUsers();

    @EnvelopePayload("items")
    @GET("/users/{userId}/top-tags") Call<List<Tag>> getTags(@Path("userId") int userId);

    @EnvelopePayload("items")
    @GET("/users/{userId}/badges") Call<List<Badge>> getBadges(@Path("userId") int userId);
}
