package com.github.itbookapp.data.model

import com.google.gson.annotations.SerializedName

data class SearchData(
    @SerializedName("books")
    val books: List<Books>?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("total")
    val total: Int?
)
