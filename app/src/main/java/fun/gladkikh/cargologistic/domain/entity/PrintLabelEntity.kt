package `fun`.gladkikh.cargologistic.domain.entity

import java.util.*

data class PrintLabelEntity(val product:ProductEntity,
                            val printer:PrinterEntity,
                            val countLabel:Int,
                            val dateCreate:Date,
                            val datePrint: Date)