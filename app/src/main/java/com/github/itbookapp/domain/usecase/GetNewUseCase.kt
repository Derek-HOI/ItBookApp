package com.github.itbookapp.domain.usecase

import com.github.itbookapp.data.model.NewBookList
import com.github.itbookapp.data.model.RequestResult
import com.github.itbookapp.data.model.coroutine.IoDispatcher
import com.github.itbookapp.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetNewUseCase
@Inject
constructor(
    private val repository: BookRepository
) : BaseUseCase<Any?, NewBookList>() {

    override suspend fun execute(p: Any?): RequestResult<NewBookList> =
        repository.getNew()

}
