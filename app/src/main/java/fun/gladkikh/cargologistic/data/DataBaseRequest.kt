package `fun`.gladkikh.cargologistic.data

import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import `fun`.gladkikh.cargologistic.common.type.None
import `fun`.gladkikh.cargologistic.domain.entity.PrintLabelEntity
import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import androidx.lifecycle.LiveData

interface DataBaseRequest{
    fun getProductByBarcode(barcode:String):Either<Failure, ProductEntity>
    fun saveProduct(productEntity: ProductEntity):Either<Failure, None>
    fun removeAll():Either<Failure, None>
    fun savePrintLabel(printLabelEntity: PrintLabelEntity):Either<Failure, None>
    fun getListPrintLabel(): LiveData<List<PrintLabelEntity>>
}