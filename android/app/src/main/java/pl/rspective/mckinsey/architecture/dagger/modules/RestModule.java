package pl.rspective.mckinsey.architecture.dagger.modules;

import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.data.local.SurveyLocalStorage;
import pl.rspective.data.repository.LoginRepository;
import pl.rspective.data.repository.McKinseyLoginRepository;
import pl.rspective.data.repository.McKinseySurveyRepository;
import pl.rspective.data.repository.SurveyRepository;
import pl.rspective.data.rest.McKinseySurveyApi;
import pl.rspective.data.rest.McKinseyLoginApi;
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
        return Endpoints.newFixedEndpoint("http://furryoctoninja.apphb.com");
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
    @Named("login")
    RestAdapter provideLoginRestAdapter(Endpoint endpoint, Client client) {
        return new RestAdapter.Builder() //
                .setClient(client) //
                .setLogLevel(RestAdapter.LogLevel.FULL) //
                .setEndpoint(endpoint) //
                .build();
    }

    @Provides
    @Singleton
    @Named("general")
    RestAdapter provideGeneralRestAdapter(Endpoint endpoint, Client client, final PublishSubject<NetworkAction> publishSubject) {
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
    McKinseySurveyApi provideGeneralApi(@Named("general") RestAdapter restAdapter) {
        return restAdapter.create(McKinseySurveyApi.class);
    }

    @Provides
    @Singleton
    McKinseyLoginApi provideLoginApi(@Named("login") RestAdapter restAdapter) {
        return restAdapter.create(McKinseyLoginApi.class);
    }

    @Provides
    @Singleton
    LoginRepository provideLoginRepository(McKinseyLoginApi api) {
        return new McKinseyLoginRepository(api);
    }

    @Provides
    @Singleton
    SurveyRepository provideSurveyRepository(SurveyLocalStorage<String> surveyStorage, McKinseySurveyApi api) {
        return new McKinseySurveyRepository(surveyStorage, api);
    }

}
