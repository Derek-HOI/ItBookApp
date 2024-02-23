package com.github.itbookapp.di.module

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val apiUrl = "https://api.itbook.store/1.0/"

    @Provides
    fun provideCommonRetrofit(): Retrofit {
        val gson = GsonBuilder().disableHtmlEscaping().create()

        return Retrofit.Builder().client(OkHttpClient.Builder().build())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(apiUrl)
            .build()
    }

}
