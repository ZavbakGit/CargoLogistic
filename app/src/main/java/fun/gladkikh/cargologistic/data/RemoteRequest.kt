package `fun`.gladkikh.cargologistic.data
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure


interface RemoteRequest {
    fun request(data: String): Either<Failure, String>
}