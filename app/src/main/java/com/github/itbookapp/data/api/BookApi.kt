package com.github.itbookapp.data.api

import com.github.itbookapp.data.model.NewBookList
import retrofit2.Response
import retrofit2.http.GET

interface BookApi {

    @GET("/new")
    suspend fun getNew(): Response<NewBookList>
}