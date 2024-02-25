package com.github.itbookapp.di.module

import com.github.itbookapp.data.datasource.BookDataSource
import com.github.itbookapp.data.datasource.BookDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindBookDataSource(bookDataSourceImpl: BookDataSourceImpl): BookDataSource

}
