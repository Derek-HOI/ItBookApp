package com.github.itbookapp.presentation.viewmodel.event

import com.github.itbookapp.data.model.RequestResult

interface BaseEvent {
    val result: RequestResult<Any>
}
