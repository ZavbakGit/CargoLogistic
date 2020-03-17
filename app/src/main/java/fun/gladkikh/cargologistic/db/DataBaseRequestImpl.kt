package `fun`.gladkikh.cargologistic.db

import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.data.DataBaseRequest
import `fun`.gladkikh.cargologistic.db.dao.ProductDao
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import android.content.Context

class DataBaseRequestImpl(
    context: Context
    ) : DataBaseRequest {

    var appDatabase: AppDatabase = AppDatabase.getInstance(context)
    var productDao: ProductDao


    init {
        productDao = appDatabase.productDao()
    }

    override fun getProductByBarcode(barcode: String): Either<Failure, ProductEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveProduct(productEntity: ProductEntity): Either<Failure, None> {
        try {

        } catch (e: Exception) {
            return Either.Left(Failure("Ошибка сохранения продукта!"))
        }
    }

    override fun removeAll(): Either<Failure, None> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}