package com.github.itbookapp.domain.usecase

import com.github.itbookapp.data.model.RequestResult
import com.github.itbookapp.data.model.SearchData
import com.github.itbookapp.data.model.coroutine.IoDispatcher
import com.github.itbookapp.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class SearchUseCase
@Inject
constructor(
    private val repository: BookRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<SearchUseCase.Params, SearchData>(dispatcher) {

    override suspend fun execute(p: Params): Flow<RequestResult<SearchData>> =
        repository.search(p.query, p.page)

    data class Params(
        val query: String,
        val page: Int
    )
}
