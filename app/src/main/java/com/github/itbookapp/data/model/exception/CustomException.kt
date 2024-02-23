package com.github.itbookapp.data.model.exception

class CustomException : Exception {

    constructor() : super()
    constructor(msg: String) : super(msg)
    constructor(msg: String, cause: Throwable) : super(msg, cause)
    constructor(cause: Throwable) : super(cause)

    var httpCode: Int = -1
    var httpMessage: String = ""
    var errorBody: String? = null

    override fun toString(): String =
        "cause= $cause;\nmessage= $message;\nhttpCode= $httpCode; httpMessage= $httpMessage;"
}
