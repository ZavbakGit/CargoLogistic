package `fun`.gladkikh.cargologistic.data


import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure

interface Remote {
    fun request(user:String,password:String,data: String): Either<Failure, String>
}