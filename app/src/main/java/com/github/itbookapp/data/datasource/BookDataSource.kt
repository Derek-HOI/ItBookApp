package com.github.itbookapp.data.datasource

import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.NewBookList
import com.github.itbookapp.data.model.SearchData

interface BookDataSource {

    suspend fun search(
        query: String,
        page: Int
    ): SearchData

    suspend fun getNew(): NewBookList

    suspend fun getBooks(
        isbn13: String
    ): Books

}
