package `fun`.gladkikh.cargologistic.mapper

import `fun`.gladkikh.cargologistic.db.entity.BarcodeDb
import `fun`.gladkikh.cargologistic.db.entity.ProductDb
import `fun`.gladkikh.cargologistic.db.entity.UnitDb
import `fun`.gladkikh.cargologistic.domain.entity.BarcodeEntity
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity
import `fun`.gladkikh.cargologistic.domain.entity.UnitEntity


fun ProductEntity.transform() = ProductDb(
    guid = this.guid,
    name = this.name
)

fun ProductDb.transform(listUnitDb:List<UnitDb>,listBarcode:List<BarcodeDb>) = ProductEntity(
    guid = this.guid,
    name = this.name,
    listBarcode = listBarcode.map { it.transform() },
    listUnit = listUnitDb.map { it.transform() }
)

fun BarcodeEntity.transform(productEntity: ProductEntity) =
    BarcodeDb(
        guidProduct = productEntity.guid,
        barcode = this.barcode
    )


fun BarcodeDb.transform() = BarcodeEntity(
    barcode = this.barcode
)

fun UnitEntity.transform(productEntity: ProductEntity) = UnitDb(
    code = this.code,
    guidProduct = productEntity.guid,
    name = this.name,
    current = this.current,
    coeff = this.coeff
)

fun UnitDb.transform() = UnitEntity(
    code = this.code,
    coeff = this.coeff,
    current = this.current,
    name = this.name
)