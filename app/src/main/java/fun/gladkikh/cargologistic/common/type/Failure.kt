package `fun`.gladkikh.cargologistic.common.type

import `fun`.gladkikh.cargologistic.remote.core.ErrorDescriptionEntity

open class Failure(var message: String? = null)
open class NetworkConnectionFailure : Failure("Ошибка соеденения!")
open class ErrorDescriptionFailure(val errorDescriptionEntity: ErrorDescriptionEntity)
    :Failure("Ошибка сервера")

open class SettingsFailure:Failure("Ошибка заполнения настроек!")
open class AccountFailure:Failure("Ошибка чтения аккаута")

