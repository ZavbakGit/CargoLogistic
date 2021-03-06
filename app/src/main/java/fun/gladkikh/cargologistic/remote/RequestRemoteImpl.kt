package `fun`.gladkikh.cargologistic.remote


import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.PreferencesFailure
import `fun`.gladkikh.cargologistic.data.RemoteRequest
import `fun`.gladkikh.cargologistic.remote.core.AutorithationUtil
import `fun`.gladkikh.cargologistic.remote.core.NetworkHandler
import `fun`.gladkikh.cargologistic.remote.core.Request
import `fun`.gladkikh.cargologistic.remote.entity.RequestEntity
import `fun`.gladkikh.cargologistic.remote.service.ServiceFactory

import android.content.Context


class RequestRemoteImpl constructor(
    context: Context,
    isDebug: Boolean,
    val baseUrl: String,
    val user:String,
    val password: String
) : RemoteRequest {

    private val request = Request(NetworkHandler(context))
    private val service = ServiceFactory(baseUrl).makeService(isDebug)

    override fun request(
        data: String
    ): Either<Failure, String> {

        if (baseUrl.equals("http://0.0.0.0/",true)){
            return Either.Left(PreferencesFailure("Не заполнен host в настройках"))
        }

        val auth = AutorithationUtil.getStringAutorization(user, password)
        return request.make(service.getDataFromServer(auth, RequestEntity(data))) {
            it.data
        }

    }
}