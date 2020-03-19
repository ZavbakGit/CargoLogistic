package `fun`.gladkikh.cargologistic.data


import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.Constants
import `fun`.gladkikh.cargologistic.common.type.*
import `fun`.gladkikh.cargologistic.common.utils.toFormatISO
import `fun`.gladkikh.cargologistic.domain.entity.*
import `fun`.gladkikh.cargologistic.domain.repository.AccountRepository
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.util.*

class AccountRepositoryImpl(
    private val preferences: PreferencesRequest,
    private val dataBaseRequest: DataBaseRequest,
    private val gson: Gson
) : AccountRepository {


    override fun getAccountEntity(): Either<Failure, AccountEntity> {
        return preferences.getAccountEntity()
    }

    override fun saveAccountEntity(account: AccountEntity): Either<Failure, None> {
        return dataBaseRequest.removeAll().flatMap {
            preferences.saveAccountEntity(account)
        }
    }

    override fun testRemoteRequest(): Either<Failure, String> {
        val data = TestRemoteRequestDataRq("test")
        return preferences.getSettings().flatMap {
            try {
                App.requestRemote!!.request(gson.toJson(data))
            } catch (e: Exception) {
                return@flatMap Either.Left(Failure(e.toString()))
            }
        }
    }

    override fun getProductByBarcode(barcode: String): Either<Failure, ProductEntity> {

        var data: GetProductByBarcodeRqData? = null

        val resultDb = dataBaseRequest.getProductByBarcode(barcode)

        if (resultDb.isRight) {
            return resultDb
        }

        return preferences.getAccountEntity()
            .flatMap {
                try {
                    data = GetProductByBarcodeRqData(
                        command = "get_product",
                        barcode = barcode,
                        codeTSD = Constants.UID,
                        guidUser = it.guid!!
                    )
                    return@flatMap Either.Right(data)
                } catch (e: Exception) {
                    return@flatMap Either.Left(Failure(e.toString()))
                }
            }.flatMap {
                preferences.getSettings()

            }.flatMap { settings ->
                try {
                    App.requestRemote!!.request(gson.toJson(data))
                        .map {
                            val response = gson.fromJson(it, GetProductByBarcodeRsData::class.java)

                            return@map ProductEntity(
                                guid = response.guidProduct!!,
                                listBarcode = response.barcodes.map { barcode ->
                                    BarcodeEntity(barcode.barcode)
                                },
                                name = response.name,
                                listUnit = response.listUnit
                            )
                        }
                } catch (e: Exception) {
                    return@flatMap Either.Left(Failure(e.toString()))
                }
            }.onNext {
                dataBaseRequest.saveProduct(it)

            }


    }

    override fun login(password: String): Either<Failure, AccountEntity> {
        val data = LoginDataRq("login", password)
        return preferences.getSettings()
            .flatMap { settings ->
                try {
                    App.requestRemote!!.request(gson.toJson(data))
                        .map {
                            gson.fromJson(it, AccountEntity::class.java)
                        }
                } catch (e: Exception) {
                    return@flatMap Either.Left(Failure(e.toString()))
                }
            }
    }


    override fun printLabel(printLabelEntity: PrintLabelEntity): Either<Failure, PrintLabelEntity> {
        var data: printLabelRqData? = null

        return preferences.getAccountEntity()
            .flatMap {
                try {
                    data = printLabelRqData(
                        command = "print_lable",
                        barcode = printLabelEntity.barcodeRead,
                        codeTSD = Constants.UID,
                        guidUser = it.guid!!,
                        count = printLabelEntity.countLabel,
                        guidProduct = printLabelEntity.product.guid,
                        dateCreate = printLabelEntity.dateCreate.toFormatISO(),
                        guidPrinter = printLabelEntity.printer.guid
                    )
                    return@flatMap Either.Right(data)
                } catch (e: Exception) {
                    return@flatMap Either.Left(Failure(e.toString()))
                }
            }
            .flatMap {
                preferences.getSettings()
            }
            .flatMap { settings ->
                try {
                    App.requestRemote!!.request(gson.toJson(data))
                        .map {
                            val response = gson.fromJson(it, printLabelRsData::class.java)
                            return@map printLabelEntity.copy(datePrint = response.date)
                        }
                } catch (e: Exception) {
                    return@flatMap Either.Left(Failure(e.toString()))
                }
            }
            .onNext {
                dataBaseRequest.savePrintLabel(it)
            }

    }

    private class printLabelRqData(
        val command: String,
        @SerializedName("code_tsd")
        val codeTSD: String?,
        @SerializedName("guid_user")
        val guidUser: String?,
        @SerializedName("guid_printer")
        val guidPrinter: String?,
        @SerializedName("count")
        val count: Int,
        @SerializedName("guid_product")
        val guidProduct: String,
        @SerializedName("date_create")
        val dateCreate: String,
        @SerializedName("barcode")
        val barcode: String?
    )

    private class printLabelRsData(
        val date: Date
    )

    private data class GetProductByBarcodeRqData(
        val command: String,
        @SerializedName("code_tsd")
        val codeTSD: String?,
        @SerializedName("guid_user")
        val guidUser: String,
        val barcode: String
    )

    private data class GetProductByBarcodeRsData(
        @SerializedName("guid_product")
        val guidProduct: String?,
        val name: String,
        val barcodes: List<Barcode>,
        @SerializedName("units")
        val listUnit: List<UnitEntity>
    )

    private data class Barcode(val barcode: String)
    private data class LoginDataRq(val command: String, val password: String)
    private data class TestRemoteRequestDataRq(val command: String)

}