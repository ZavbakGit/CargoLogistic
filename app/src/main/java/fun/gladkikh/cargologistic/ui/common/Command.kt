package `fun`.gladkikh.cargologistic.ui.common

sealed class Command
data class OpenFormCommand(val id:Int, val data:Any? = null):Command()