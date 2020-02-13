package `fun`.gladkikh.cargologistic.domain.entity

data class AccountEntity(
    val user: String?,
    val guid: String?,
    val password: String?,
    var settings: SettingsUserEntity?
)


