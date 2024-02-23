package com.github.itbookapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.itbookapp.presentation.viewmodel.event.BaseEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class BaseViewModel<E : BaseEvent>(
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    open val viewModelName: String = "BaseViewModel"

    private val _eventFlow: MutableSharedFlow<E> = MutableSharedFlow()
    val eventFlow: SharedFlow<E> = _eventFlow.asSharedFlow()

    private val viewModelContext: CoroutineContext
        get() = ioDispatcher + SupervisorJob()

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(context = viewModelContext) {
            block()
        }
    }

    protected fun event(event: E) {
        launch {
            _eventFlow.emit(event)
        }
    }
}
