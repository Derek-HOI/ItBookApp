package com.github.itbookapp.di.module

import com.github.itbookapp.domain.repository.BookRepository
import com.github.itbookapp.domain.repository.BookRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindBookRepository(bookImpl: BookRepositoryImpl): BookRepository

}
