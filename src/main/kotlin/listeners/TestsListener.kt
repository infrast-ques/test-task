package listeners

import api.services.apiClientTodos
import api.services.todosDelete
import io.qameta.allure.Step
import org.java_websocket.client.WebSocketClient
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.net.HttpURLConnection

val threadLocalTodosIds: ThreadLocal<MutableSet<ULong>> by lazy { ThreadLocal.withInitial { mutableSetOf() } }
val threadLocalWebSocket: ThreadLocal<MutableSet<WebSocketClient>> by lazy { ThreadLocal.withInitial { mutableSetOf() } }

class TestsListener : AfterEachCallback, AfterAllCallback {

    // todo Можно добавить зачистку всех тудушек перед запуском всех тестов
    //  и тогда использовать генератор айдишников с инкрементом

    @Step("[JUNIT][AFTER EACH] Post test fixture")
    override fun afterEach(context: ExtensionContext?) {
        closeWsConnection(threadLocalWebSocket.get())
    }

    @Step("[JUNIT][AFTER ALL] Post test fixture")
    override fun afterAll(context: ExtensionContext?) {
        removeTodos(threadLocalTodosIds.get().map { it })
    }

    @Step("Todos delete")
    private fun removeTodos(ids: Collection<ULong>) {
        ids.forEach {
            apiClientTodos.send(todosDelete(id = it).apply {
                statusCode = setOf(HttpURLConnection.HTTP_NO_CONTENT, HttpURLConnection.HTTP_NOT_FOUND)
            })
        }
    }

    @Step("Close WebSocket connection")
    private fun closeWsConnection(wsClients: MutableSet<WebSocketClient>) {
        wsClients.forEach {
            it.closeBlocking()
        }
    }
}
