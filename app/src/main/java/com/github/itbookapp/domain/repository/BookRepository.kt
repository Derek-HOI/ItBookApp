package com.github.itbookapp.domain.repository

import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.NewBookList
import com.github.itbookapp.data.model.RequestResult
import com.github.itbookapp.data.model.SearchData
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun search(
        query: String,
        page: Int
    ): Flow<RequestResult<SearchData>>

    suspend fun getNew(): Flow<RequestResult<NewBookList>>

    suspend fun getBooks(
        isbn13: String
    ): Flow<RequestResult<Books>>

}
