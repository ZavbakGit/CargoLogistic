package `fun`.gladkikh.cargologistic.data

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.flatMap
import `fun`.gladkikh.cargologistic.common.type.map
import `fun`.gladkikh.cargologistic.domain.entity.OrderEntity
import com.google.gson.Gson

class UtilRepositoty( private val preferences: Preferences,
                      private val gson: Gson
) {

    fun reqest(data:String):Either<Failure,Any>{
       return preferences.getSettings()
            .flatMap { settings ->
                try {
                    App.requestRemote!!.request(
                        settings.login1C!!,
                        settings.password1C!!,
                        gson.toJson(data)
                    )
                        .map {
                            gson.fromJson(it, OrderEntity::class.java)
                        }
                } catch (e: Exception) {
                    return@flatMap Either.Left(Failure(e.toString()))
                }
            }
    }

}