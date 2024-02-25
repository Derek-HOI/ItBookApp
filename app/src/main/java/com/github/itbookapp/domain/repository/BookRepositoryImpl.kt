package com.github.itbookapp.domain.repository

import com.github.itbookapp.data.datasource.BookDataSource
import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.NewBookList
import com.github.itbookapp.data.model.RequestResult
import com.github.itbookapp.data.model.SearchData
import javax.inject.Inject

class BookRepositoryImpl
@Inject
constructor(
    private val dataSource: BookDataSource
) : BookRepository {

    override suspend fun search(query: String, page: Int): RequestResult<SearchData> {
        return try {
            RequestResult.Success(dataSource.search(query, page))
        } catch (e: Exception) {
            RequestResult.Error(e)
        }
    }

    override suspend fun getNew(): RequestResult<NewBookList> {
        return try {
            RequestResult.Success(dataSource.getNew())
        } catch (e: Exception) {
            RequestResult.Error(e)
        }
    }

    override suspend fun getBooks(isbn13: String): RequestResult<Books> {
        return try {
            RequestResult.Success(dataSource.getBooks(isbn13))
        } catch (e: Exception) {
            RequestResult.Error(e)
        }
    }

}
