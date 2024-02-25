package com.github.itbookapp.data.api

import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.NewBookList
import com.github.itbookapp.data.model.SearchData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {

    @GET("/1.0/search/{query}/{page}")
    suspend fun search(
        @Path("query")
        query: String,
        @Path("page")
        page: Int
    ): Response<SearchData>

    @GET("/1.0/new")
    suspend fun getNew(): Response<NewBookList>

    @GET("/1.0/books/{isbn13}")
    suspend fun getBooks(
        @Path("isbn13")
        isbn13: String
    ): Response<Books>
}
