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
) : BaseUseCase<GetBooksUseCase.Params, Books>() {

    override suspend fun execute(p: Params): RequestResult<Books> =
        repository.getBooks(p.isbn13)

    data class Params(
        val isbn13: String
    )
}
