package com.github.itbookapp.data.datasource

import com.github.itbookapp.data.api.BookApi
import com.github.itbookapp.ext.verify
import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.NewBookList
import com.github.itbookapp.data.model.SearchData
import javax.inject.Inject

class BookDataSourceImpl
@Inject
constructor(
    private val api: BookApi
) : BookDataSource {

    override suspend fun search(query: String, page: Int): SearchData {
        return api.search(query, page).verify()
    }

    override suspend fun getNew(): NewBookList {
        return api.getNew().verify()
    }

    override suspend fun getBooks(isbn13: String): Books {
        return api.getBooks(isbn13).verify()
    }
}
