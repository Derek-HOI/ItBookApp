package com.github.itbookapp.data.model

import com.google.gson.annotations.SerializedName

data class NewBookList(
    @SerializedName("books")
    val books: List<Books>,
    @SerializedName("total")
    val total: String
)
