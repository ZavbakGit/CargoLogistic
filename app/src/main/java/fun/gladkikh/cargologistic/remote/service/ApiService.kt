package `fun`.gladkikh.cargologistic.remote.service

import `fun`.gladkikh.cargologistic.remote.entity.RequestEntity
import `fun`.gladkikh.cargologistic.remote.entity.ResponseEntity
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("host")
    fun getDataFromServer(
        @Header("Authorization") auth: String,
        @Body requestEntity: RequestEntity
    ): Call<ResponseEntity>
}