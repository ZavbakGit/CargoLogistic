package `fun`.gladkikh.cargologistic.db

import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.data.DataBaseRequest
import `fun`.gladkikh.cargologistic.db.dao.ProductDao
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import `fun`.gladkikh.cargologistic.mapper.transform
import android.content.Context

class DataBaseRequestImpl(
    val productDao: ProductDao
) : DataBaseRequest {

    override fun getProductByBarcode(barcode: String): Either<Failure, ProductEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveProduct(productEntity: ProductEntity): Either<Failure, None> {
        try {
            val productDb = productEntity.transform()
            val listBarcodeDb = productEntity.listBarcode.map { it.transform(productEntity) }
            val listUnitDb = productEntity.listUnit.map { it.transform(productEntity) }

            productDao.insertOrUpdate(productDb)

            listBarcodeDb.forEach {
                productDao.insertOrUpdate(it)
            }

            listUnitDb.forEach {
                productDao.insertOrUpdate(it)
            }

            return Either.Right(None())

        } catch (e: Exception) {
            return Either.Left(Failure("Ошибка сохранения продукта!"))
        }
    }

    override fun removeAll(): Either<Failure, None> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}