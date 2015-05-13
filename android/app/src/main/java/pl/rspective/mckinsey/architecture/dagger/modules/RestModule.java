package pl.rspective.mckinsey.architecture.dagger.modules;

import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.data.rest.McKinseyApi;
import pl.rspective.data.rest.NetworkAction;
import pl.rspective.data.rest.converter.RestDataConverter;
import pl.rspective.mckinsey.McKinseyApp;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.ErrorHandler;
import retrofit.Profiler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.OkClient;
import rx.subjects.PublishSubject;

@Module(
        injects = {
        },
        includes = {
        },
        complete = false,
        library = true
)
public final class RestModule {

    static final int DISK_CACHE_SIZE = 20 * 1024 * 1024; // 20MB

    @Provides
    @Singleton
    public PublishSubject<NetworkAction> provideNetworkObservable() {
        return PublishSubject.create();
    }

    @Provides
    @Singleton
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint("");
    }

    @Provides
    @Singleton
    Client provideClient(OkHttpClient client) {
        return new OkClient(client);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(McKinseyApp app) {
        OkHttpClient client = new OkHttpClient();

        try {
            File cacheDir = new File(app.getCacheDir(), "http");
            Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
            client.setCache(cache);
        } catch (IOException e) {
            Log.e("DataModule", "Unable to install disk cache.");
        }

        return client;
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(Endpoint endpoint, Client client, final PublishSubject<NetworkAction> publishSubject) {
        return new RestAdapter.Builder() //
                .setClient(client) //
                .setLogLevel(RestAdapter.LogLevel.FULL) //
                .setEndpoint(endpoint) //
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError error) {
                        publishSubject.onError(error);
                        return error;
                    }
                })
                .setProfiler(new Profiler() {
                    @Override
                    public Object beforeCall() {
                        publishSubject.onNext(NetworkAction.HTTP_REQUEST_START);
                        return null;
                    }

                    @Override
                    public void afterCall(RequestInformation requestInfo, long elapsedTime, int statusCode, Object beforeCallData) {
                        publishSubject.onNext(NetworkAction.HTTP_REQUEST_END);
                    }
                })
                .setConverter(new RestDataConverter())
                .build();
    }

    @Provides
    @Singleton
    McKinseyApi provideApi(RestAdapter restAdapter) {
        return restAdapter.create(McKinseyApi.class);
    }

}
