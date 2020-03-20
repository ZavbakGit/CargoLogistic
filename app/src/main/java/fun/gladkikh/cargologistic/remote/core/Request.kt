package `fun`.gladkikh.cargologistic.remote.core

import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.ErrorDescriptionFailure
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.NetworkConnectionFailure

import `fun`.gladkikh.cargologistic.remote.entity.ResponseEntity


import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response


class Request constructor(private val networkHandler: NetworkHandler) {

    fun <T : ResponseEntity, R> make(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
        return when (networkHandler.isConnected) {
            true -> execute(call, transform)
            false, null -> Either.Left(NetworkConnectionFailure())
        }
    }

    private fun <T : ResponseEntity, R> execute(
        call: Call<T>,
        transform: (T) -> R
    ): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSucceed()) {
                true -> Either.Right(transform((response.body()!!)))
                false -> Either.Left(response.parseError())
            }
        } catch (exception: Throwable) {
            Either.Left(Failure(exception.toString()))
        }
    }
}

fun <T : ResponseEntity> Response<T>.isSucceed(): Boolean {
    return isSuccessful && body() != null && (body() as ResponseEntity).success == 1
}

fun <T : ResponseEntity> Response<T>.parseError(): Failure {
    try {
        val message = (body() as ResponseEntity).error
        val builder = GsonBuilder()
        val gson = builder.create()
        val error = gson.fromJson(message, ErrorDescriptionEntity::class.java)
        return ErrorDescriptionFailure(
            ErrorDescriptionEntity(
                code = error.code,
                description = error.description,
                extra = error.extra
            )
        )
    } catch (e: Exception) {
        return Failure(this.toString())
    }
}



