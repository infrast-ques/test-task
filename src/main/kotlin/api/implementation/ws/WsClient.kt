package api.implementation.ws

import io.qameta.allure.Allure
import io.qameta.allure.Step
import listeners.threadLocalWebSocket
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import utils.invoke
import java.net.URI
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class WsClient(
    val serverUri: URI,
    val timeout: Duration = Duration.ofSeconds(60),
    val context: WsContext = WsContext(),
) : WebSocketClient(serverUri) {

    private val currentTime get() = LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME)

    init {
        threadLocalWebSocket.get().add(this)
        Runtime.getRuntime().addShutdownHook(Thread {
            if (isOpen) {
                closeBlocking()
            }
        })
    }


    // todo Мб удалить?
    @Step("Execute script via WebSocket")
    fun executeScript(
        closeConnectionAfterTask: Boolean = false,
        block: WebSocketClient.() -> Unit
    ) {
        try {
            if (isOpen.not()) {
                "Establish WebSocket connection" {
                    connectBlocking()
                }
            }

            "Execute script" {
                block()
            }

        } catch (e: Exception) {
            Allure.addAttachment("Error", e.message)
            throw e
        } finally {
            if (closeConnectionAfterTask) {
                closeBlocking()
            }
        }
    }

    override fun closeBlocking() {
        super.closeBlocking()
        threadLocalWebSocket.get().remove(this)
    }

    override fun send(text: String) {
        "$currentTime - Send message: $text" {
            println(text)
            super.send(text)
        }
    }

    override fun onOpen(handshake: ServerHandshake?) {
        "$currentTime - Opened connection: $serverUri" {
            println("$currentTime - Opened connection: $serverUri")
        }
    }

    override fun onMessage(message: String?) {
        "$currentTime - Received message: " {
            Allure.addAttachment("$currentTime - Received message", message)
            context.receivedMessages.add(message)
            println("$currentTime - Received message: $message")
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        "$currentTime - Closed connection" {
            context.onCloseData = WsContext.OnCloseData(
                responseCode = code,
                reason = reason,
                remote = remote,
            )
        }
    }

    override fun onError(error: Exception) {
        "$currentTime - Received error: ${error.message}" {
            Allure.addAttachment("$currentTime - Received error", error.message)
            context.errors.add(WsContext.Error(message = error.message, time = LocalDateTime.parse(currentTime)))
        }
    }
}
