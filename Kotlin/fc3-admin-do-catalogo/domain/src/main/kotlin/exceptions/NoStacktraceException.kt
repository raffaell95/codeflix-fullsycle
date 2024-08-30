package exceptions

open class NoStacktraceException(
    message: String,
    cause: Throwable?
) : RuntimeException(message, cause, true, false) {

    constructor(message: String) : this(message, null)
}