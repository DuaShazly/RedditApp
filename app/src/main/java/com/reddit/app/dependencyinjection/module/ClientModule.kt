package com.reddit.app.dependencyinjection.module

import com.reddit.app.client.RedditClient
import com.google.gson.GsonBuilder
import com.google.gson.FieldNamingPolicy
import com.reddit.app.model.RedditObject
import com.reddit.app.deserializer.RedditObjectJsonDeserializer
import com.reddit.app.deserializer.RedditDateDeserializer
import dagger.Module
import dagger.Provides
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.BuildConfig
import rx.schedulers.Schedulers
import java.util.*

@Module
class ClientModule {

    @Provides
    fun provideRedditClient(): RedditClient {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(RedditObject::class.java, RedditObjectJsonDeserializer())
            .registerTypeAdapter(Date::class.java, RedditDateDeserializer())
            .create()
        val rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io())
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(interceptor)
        }
        val client = okHttpClientBuilder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://reddit.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxAdapter)
            .build()
        return retrofit.create(RedditClient::class.java)
    }
}