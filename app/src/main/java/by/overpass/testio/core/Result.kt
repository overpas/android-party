package by.overpass.testio.core

typealias SimpleResult = Result<Unit, Unit>
typealias Success = Result.Success<Unit>
typealias Failure = Result.Failure<Unit>

sealed class Result<out T, out R> {

	data class Success<T>(val data: T) : Result<T, Nothing>()

	data class Failure<R>(val error: R) : Result<Nothing, R>()
}