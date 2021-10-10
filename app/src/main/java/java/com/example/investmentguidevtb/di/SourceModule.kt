package com.example.investmentguidevtb.di

import com.example.investmentguidevtb.data.source.api.ArticleApi
import com.example.investmentguidevtb.data.source.api.PracticeApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.google.gson.GsonBuilder

import com.google.gson.Gson
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object SourceModule {

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val client = OkHttpClient.Builder()
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun providePracticeApi(): PracticeApi {
        val baseUrl = "https://vtbhackaton.herokuapp.com/situation/";

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(PracticeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideArticleApi(): ArticleApi {
        val baseUrl = "https://vtbhackaton.herokuapp.com/articles/";

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ArticleApi::class.java)
    }
}