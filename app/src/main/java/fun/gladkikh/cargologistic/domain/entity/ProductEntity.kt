package `fun`.gladkikh.cargologistic.domain.entity

data class ProductEntity(val guid:String,
                         val name: String,
                         val listBarcode:List<String>,
                         val listUnit:List<UnitEntity>)