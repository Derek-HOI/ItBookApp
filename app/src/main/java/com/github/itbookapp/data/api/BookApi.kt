package com.github.itbookapp.data.api

import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.NewBookList
import com.github.itbookapp.data.model.SearchData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {

    @GET("/search/{query}/{page}")
    suspend fun search(
        @Path("query")
        query: String,
        @Path("page")
        page: Int
    ): Response<SearchData>

    @GET("/new")
    suspend fun getNew(): Response<NewBookList>

    @GET("/books/{isbn13}")
    suspend fun getBooks(
        @Path("isbn13")
        isbn13: String
    ): Response<Books>
}
