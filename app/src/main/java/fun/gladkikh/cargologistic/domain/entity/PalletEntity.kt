package `fun`.gladkikh.cargologistic.domain.entity

data class PalletEntity(val guid:String,
                        val number:String,
                        val order:OrderEntity? = null)