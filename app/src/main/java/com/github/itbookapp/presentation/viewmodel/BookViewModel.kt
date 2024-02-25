package com.github.itbookapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.NewBookList
import com.github.itbookapp.data.model.RequestResult
import com.github.itbookapp.data.model.SearchData
import com.github.itbookapp.data.model.coroutine.IoDispatcher
import com.github.itbookapp.data.model.pagingCount
import com.github.itbookapp.domain.usecase.GetBooksUseCase
import com.github.itbookapp.domain.usecase.GetNewUseCase
import com.github.itbookapp.domain.usecase.SearchUseCase
import com.github.itbookapp.presentation.pagingsource.BooksPagingSource
import com.github.itbookapp.presentation.viewmodel.event.BaseEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class BookViewModel
@Inject
constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val searchUseCase: SearchUseCase,
    private val getNewUseCase: GetNewUseCase,
    private val getBooksUseCase: GetBooksUseCase,
) : BaseViewModel<BookViewModel.BookEvent>(ioDispatcher) {

    var query by mutableStateOf("")

    private lateinit var pagingSource: BooksPagingSource

    val booksPager = Pager(PagingConfig(pageSize = pagingCount)) {
        BooksPagingSource(
            query = query,
            dispatcher = ioDispatcher,
            searchUseCase = searchUseCase
        ).also { pagingSource = it }
    }.flow

    fun invalidatePagingSource() {
        pagingSource.invalidate()
    }

    fun getNew() {
        launch {
            event(BookEvent.GetNew(RequestResult.Loading))
            event(BookEvent.GetNew(getNewUseCase(null)))
        }
    }

    fun getBooks(isbn13: String) {
        launch {
            event(BookEvent.GetBooks(RequestResult.Loading))
            event(BookEvent.GetBooks(getBooksUseCase(GetBooksUseCase.Params(isbn13))))
        }
    }

    sealed interface BookEvent : BaseEvent {

        data class GetNew(
            override val result: RequestResult<NewBookList>
        ) : BookEvent

        data class GetBooks(
            override val result: RequestResult<Books>
        ) : BookEvent

    }

}
