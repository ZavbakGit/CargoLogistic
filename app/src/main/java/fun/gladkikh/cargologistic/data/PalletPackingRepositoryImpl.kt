package `fun`.gladkikh.cargologistic.data

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.map
import `fun`.gladkikh.cargologistic.domain.entity.OrderEntity
import `fun`.gladkikh.cargologistic.domain.entity.PalletEntity
import `fun`.gladkikh.cargologistic.domain.repository.PalletPackingRepository
import com.google.gson.Gson

class PalletPackingRepositoryImpl(
    private val preferences: Preferences,
    private val gson: Gson
) : PalletPackingRepository {

    /**
     * Получает Заказ по штрихкоду
     */
    override fun getOrderByBarcode(barcode: String): Either<Failure, OrderEntity> {
        val data = GetOrderByBarcodeRq(barcode = barcode)

        return try {
            App.requestRemote!!.request(gson.toJson(data))
                .map {
                    gson.fromJson(it, OrderEntity::class.java)
                }
        } catch (e: Exception) {
            Either.Left(Failure(e.toString()))
        }

    }

    /**
     * Получает паллет по штрихкоду
     */
    override fun getPalletByBarcode(barcode: String): Either<Failure, PalletEntity> {
        val data = GetPalletByBarcode(barcode = barcode)

        return try {
            App.requestRemote!!.request(gson.toJson(data))
                .map {
                    gson.fromJson(it, PalletEntity::class.java)
                }
        } catch (e: Exception) {
            Either.Left(Failure(e.toString()))
        }
    }

    private data class GetOrderByBarcodeRq(val command: String = "get_order", val barcode: String)
    private data class GetPalletByBarcode(val command: String = "get_pallet", val barcode: String)

}