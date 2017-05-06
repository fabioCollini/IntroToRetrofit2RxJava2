package it.codingjam.introtoretrofitrxjava.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Badge {

    public static TypeAdapter<Badge> typeAdapter(Gson gson)
    {
        return new AutoValue_Badge.GsonTypeAdapter(gson);
    }

    public abstract String name();

    @Override public String toString() {
        return name();
    }
}
