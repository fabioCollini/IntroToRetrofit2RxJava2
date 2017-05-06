package it.codingjam.introtoretrofitrxjava;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import it.codingjam.introtoretrofitrxjava.model.MyAdapterFactory;
import it.codingjam.introtoretrofitrxjava.utils.DenvelopingConverter;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StackOverflowServiceFactory {
    public static <T> T createService(Class<T> serviceClass) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder()
                            .addQueryParameter("site", "stackoverflow")
                            .addQueryParameter("key", "fruiv4j48P0HjSJ8t7a8Gg((")
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                });

        OkHttpClient okHttpClient = builder
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(MyAdapterFactory.create())
                .create();

        return new Retrofit.Builder()
                .baseUrl("http://api.stackexchange.com/2.2/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(new DenvelopingConverter(gson))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(serviceClass);
    }
}
