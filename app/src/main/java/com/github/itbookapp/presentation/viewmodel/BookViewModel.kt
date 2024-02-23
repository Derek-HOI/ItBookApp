package com.github.itbookapp.presentation.viewmodel

import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.NewBookList
import com.github.itbookapp.data.model.RequestResult
import com.github.itbookapp.data.model.SearchData
import com.github.itbookapp.data.model.coroutine.IoDispatcher
import com.github.itbookapp.domain.usecase.GetBooksUseCase
import com.github.itbookapp.domain.usecase.GetNewUseCase
import com.github.itbookapp.domain.usecase.SearchUseCase
import com.github.itbookapp.presentation.viewmodel.event.BaseEvent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class BookViewModel
@Inject
constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val searchUseCase: SearchUseCase,
    private val getNewUseCase: GetNewUseCase,
    private val getBooksUseCase: GetBooksUseCase,
) : BaseViewModel<BookViewModel.BookEvent>(ioDispatcher) {

    sealed interface BookEvent: BaseEvent {

        data class Search(
            override val result: RequestResult<SearchData>
        ): BookEvent

        data class GetNew(
            override val result: RequestResult<NewBookList>
        ): BookEvent

        data class GetBooks(
            override val result: RequestResult<Books>
        ): BookEvent

    }

}
