package it.codingjam.introtoretrofitrxjava.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.text.MessageFormat;

@AutoValue
public abstract class Tag {

    public static TypeAdapter<Tag> typeAdapter(Gson gson)
    {
        return new AutoValue_Tag.GsonTypeAdapter(gson);
    }

    @SerializedName("tag_name")
    public abstract String name();

    @SerializedName("answer_count")
    public abstract int count();

    @Override public String toString() {
        return MessageFormat.format("{0} ({1})", name(), count());
    }
}
