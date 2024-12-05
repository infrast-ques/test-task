package api.implementation.ws

import io.qameta.allure.Allure
import utils.SerializationUtils
import java.time.LocalDateTime

data class WsContext(
    val receivedMessages: MutableList<String?> = mutableListOf(),
    var onCloseData: OnCloseData? = null,
    val errors: MutableList<Error> = mutableListOf(),
) {

    inline fun <reified T : Any> getMessages(): List<T> =
        receivedMessages.mapNotNull {
            try {
                SerializationUtils.fromJson(it!!)
            } catch (e: Exception) {
                Allure.addAttachment("Serialisation error", it)
                null
            }
        }

    data class OnCloseData(
        var responseCode: Int? = null,
        var reason: String? = null,
        var remote: Boolean? = null,
    )

    data class Error(
        val message: String?,
        val time: LocalDateTime
    )
}
