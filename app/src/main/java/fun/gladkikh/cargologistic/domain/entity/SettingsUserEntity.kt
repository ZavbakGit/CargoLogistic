package `fun`.gladkikh.cargologistic.domain.entity

import com.google.gson.annotations.SerializedName

data class SettingsUserEntity(
    val name: String?,
    val guid: String?,
    @SerializedName("list_printer")
    val listPrinter: List<PrinterEntity>
)
