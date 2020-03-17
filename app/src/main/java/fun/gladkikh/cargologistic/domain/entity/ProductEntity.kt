package `fun`.gladkikh.cargologistic.domain.entity

data class ProductEntity(val guid:String,
                         val name: String,
                         val listBarcode:List<Barcode>,
                         val listUnit:List<UnitEntity>)