package com.mauri.android.flickrexample.app.dependencyinjection.modules;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.mauri.android.flickrexample.app.Constants;
import com.mauri.android.flickrexample.app.FlickrExampleApp;
import com.mauri.android.flickrexample.app.dependencyinjection.scopes.ActivityScope;
import com.mauri.android.flickrexample.interactors.GetPhotoInfoInteractor;
import com.mauri.android.flickrexample.interactors.GetRecentPhotosInteractor;
import com.mauri.android.flickrexample.interactors.SearchPhotosInteractor;
import com.mauri.android.flickrexample.network.FlickrApi;
import com.mauri.android.flickrexample.network.deserializers.PhotoJsonDeserializer;
import com.mauri.android.flickrexample.network.responses.GetPhotoInfoResponse;
import com.mauri.android.flickrexample.network.responses.GetRecentResponse;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by mauri on 17/11/16.
 */

@Module
public class NetworkModule {


    String mBaseUrl;
    FlickrExampleApp mFlickrApp;

    public NetworkModule(FlickrExampleApp flickrExampleApp,String baseUrl) {
        this.mBaseUrl = baseUrl;
        this.mFlickrApp = flickrExampleApp;
    }

    @Provides
    @Singleton
    FlickrExampleApp providesApp(){
        return mFlickrApp;
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(FlickrExampleApp flickrExampleApp) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(flickrExampleApp.getCacheDir(), cacheSize);
        return cache;
    }


    @Provides  // Dagger will only look for methods annotated with @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.registerTypeAdapter(GetPhotoInfoResponse.class, new PhotoJsonDeserializer());
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, final FlickrExampleApp flickrExampleApp) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder req = chain.request().newBuilder();
                        HttpUrl.Builder urlBuilder = req.build().url().newBuilder();
                        urlBuilder.addQueryParameter("api_key", Constants.FLICKR_API_KEY)
                                .addQueryParameter("format", "json")
                                .addQueryParameter("nojsoncallback", "1");
                        req.url(urlBuilder.build());
                        Response resp = chain.proceed
                                (req.build());
                        if (resp.cacheResponse()!=null)
                            Timber.d("HIT FROM DISK CACHE");
                        return resp;
                    }
                })
                .cache(cache)
                .build();

        Glide.get(flickrExampleApp.getApplicationContext())
                .register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
        return okHttpClient;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    FlickrApi provideFlickr(Retrofit retrofit){
        return retrofit.create(FlickrApi.class);
    }

    @Provides
    @Singleton
    public GetPhotoInfoInteractor providesGetPhotoInfoInteractor(FlickrApi flickrApi){
        return new GetPhotoInfoInteractor(flickrApi);
    }

    @Provides
    @Singleton
    public GetRecentPhotosInteractor providesGetRecentPhotosInteractor(FlickrApi flickrApi){
        return new GetRecentPhotosInteractor(flickrApi);
    }
    @Provides
    @Singleton
    public SearchPhotosInteractor providesSearchPhotosInteractor(FlickrApi flickrApi){
        return new SearchPhotosInteractor(flickrApi);
    }
}
