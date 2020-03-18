package `fun`.gladkikh.cargologistic.db

import `fun`.gladkikh.cargologistic.common.type.*
import `fun`.gladkikh.cargologistic.data.DataBaseRequest
import `fun`.gladkikh.cargologistic.db.dao.ProductDao
import `fun`.gladkikh.cargologistic.db.entity.ProductDb
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import `fun`.gladkikh.cargologistic.mapper.transform
import android.content.Context

class DataBaseRequestImpl(
    private val productDao: ProductDao
) : DataBaseRequest {

    override fun getProductByBarcode(barcode: String): Either<Failure, ProductEntity> {
        try {
            val productDb =
                productDao.getProductByBarcode(barcode) ?: return Either.Left(NotFoundInDB())

            val listBarcode = productDao.getBarcodeListByProduct(productDb.guid)
            val listUnit = productDao.getUnitListByProduct(productDb.guid)

            return Either.Right(productDb.transform(listUnit, listBarcode))

        } catch (e: Exception) {
            return Either.Left(FailureInDB())
        }
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
            return Either.Left(FailureInDB())
        }
    }

    override fun removeAll(): Either<Failure, None> {
        return try {
            productDao.deleteAllProduct()
            Either.Right(None())
        } catch (e: Exception) {
            Either.Left(FailureInDB())
        }
    }

}