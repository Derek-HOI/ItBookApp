package com.github.itbookapp.di.module

import com.github.itbookapp.data.api.BookApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideBookApi(
        retrofit: Retrofit
    ): BookApi = provideApi(retrofit)

}

inline fun <reified I> provideApi(retrofit: Retrofit): I = retrofit.create(I::class.java)
