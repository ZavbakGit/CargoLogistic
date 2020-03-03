package `fun`.gladkikh.cargologistic.data

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.flatMap
import `fun`.gladkikh.cargologistic.common.type.map
import `fun`.gladkikh.cargologistic.domain.entity.OrderEntity
import `fun`.gladkikh.cargologistic.domain.entity.PalletEntity
import `fun`.gladkikh.cargologistic.domain.repository.PalletPackingRepository
import com.google.gson.Gson

class PalletPackingRepositoryImpl(
    private val preferences: Preferences,
    private val gson: Gson
) : PalletPackingRepository {
    override fun getOrderByBarcode(barcode: String): Either<Failure, OrderEntity> {
        val data = GetOrderByBarcodeRq(barcode = barcode)

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

    override fun getPalletByBarcode(barcode: String): Either<Failure, PalletEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private data class GetOrderByBarcodeRq(val command: String = "get_order", val barcode: String)
    private data class GetPalletByBarcode(val command: String = "get_pallet", val barcode: String)

}