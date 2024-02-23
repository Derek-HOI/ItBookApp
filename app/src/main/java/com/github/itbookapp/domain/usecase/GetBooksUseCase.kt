package com.github.itbookapp.domain.usecase

import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.RequestResult
import com.github.itbookapp.data.model.coroutine.IoDispatcher
import com.github.itbookapp.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetBooksUseCase
@Inject
constructor(
    private val repository: BookRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<GetBooksUseCase.Params, Books>(dispatcher) {

    override suspend fun execute(p: Params): Flow<RequestResult<Books>> =
        repository.getBooks(p.isbn13)

    data class Params(
        val isbn13: String
    )
}
