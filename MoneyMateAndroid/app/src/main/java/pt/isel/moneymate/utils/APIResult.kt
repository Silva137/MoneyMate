package pt.isel.moneymate.utils
    sealed class APIResult<out T> {

        class Success<out T>(val data: T): APIResult<T>()

        class Error(val message: String): APIResult<Nothing>()
}