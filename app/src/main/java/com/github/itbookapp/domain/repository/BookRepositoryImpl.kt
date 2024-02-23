package com.github.itbookapp.domain.repository

import com.github.itbookapp.data.datasource.BookDataSource
import com.github.itbookapp.ext.asFlowResponse
import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.NewBookList
import com.github.itbookapp.data.model.RequestResult
import com.github.itbookapp.data.model.SearchData
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.job
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class BookRepositoryImpl
@Inject
constructor(
    private val dataSource: BookDataSource
) : BookRepository {

    private suspend fun getJobContext(): CoroutineContext = coroutineContext + SupervisorJob()

    private var searchJob: Job? = null

    private suspend fun searchCancel() {
        searchJob?.cancel()
        searchJob?.join()
        searchJob = null
    }

    override suspend fun search(query: String, page: Int): Flow<RequestResult<SearchData>> {
        searchCancel()
        val context = getJobContext()
        return withContext(context) {
            searchJob = context.job

            delay(500)
            return@withContext asFlowResponse("search") {
                dataSource.search(query, page)
            }.also {
                searchCancel()
            }
        }
    }

    override suspend fun getNew(): Flow<RequestResult<NewBookList>> {
        return asFlowResponse("getNew") {
            dataSource.getNew()
        }
    }

    override suspend fun getBooks(isbn13: String): Flow<RequestResult<Books>> {
        return asFlowResponse("getBooks") {
            dataSource.getBooks(isbn13)
        }
    }

}
