package `fun`.gladkikh.cargologistic.remote
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure


interface RequestRemote {
    fun request(user:String,password:String,data: String): Either<Failure, String>
}