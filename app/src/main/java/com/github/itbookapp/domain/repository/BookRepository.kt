package com.github.itbookapp.domain.repository

import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.NewBookList
import com.github.itbookapp.data.model.RequestResult
import com.github.itbookapp.data.model.SearchData

interface BookRepository {

    suspend fun search(
        query: String,
        page: Int
    ): RequestResult<SearchData>

    suspend fun getNew(): RequestResult<NewBookList>

    suspend fun getBooks(
        isbn13: String
    ): RequestResult<Books>

}
