package `fun`.gladkikh.cargologistic.mapper

import `fun`.gladkikh.cargologistic.App
import `fun`.gladkikh.cargologistic.db.entity.PrintLabelDb
import `fun`.gladkikh.cargologistic.domain.entity.PrintLabelEntity
import `fun`.gladkikh.cargologistic.domain.entity.PrinterEntity
import `fun`.gladkikh.cargologistic.domain.entity.ProductEntity

fun PrintLabelEntity.transform() = PrintLabelDb(
    guid = this.guid,
    barcodeRead = this.barcodeRead,
    countLabel = this.countLabel,
    dateCreate = this.dateCreate,
    datePrint = this.datePrint,
    printerObj = App.gson.toJson(this.printer),
    productObj = App.gson.toJson(this.product)
)

fun PrintLabelDb.transform() = PrintLabelEntity(
    guid = this.guid,
    datePrint = this.datePrint,
    dateCreate = this.dateCreate,
    countLabel = this.countLabel,
    barcodeRead = this.barcodeRead,
    printer = App.gson.fromJson(this.printerObj,PrinterEntity::class.java),
    product = App.gson.fromJson(this.productObj,ProductEntity::class.java)
)