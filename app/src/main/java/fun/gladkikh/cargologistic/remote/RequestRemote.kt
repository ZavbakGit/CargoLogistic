package `fun`.gladkikh.cargologistic.remote
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure


interface RequestRemote {
    fun request(data: String): Either<Failure, String>
}