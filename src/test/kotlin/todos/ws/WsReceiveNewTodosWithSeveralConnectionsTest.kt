package todos.ws

import api.dto.TodosWSResponse
import api.services.getTodosWsClient
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import todos.TodosBaseTest
import todos.TodosWS
import utils.invoke
import utils.wait

@TodosWS
class WsReceiveNewTodosWithSeveralConnectionsTest : TodosBaseTest() {

    private val createRequests = List(5) { todoUtils.todoRequestData() }
    private val wsClients = listOf(getTodosWsClient(), getTodosWsClient(), getTodosWsClient())

    @Test
    @DisplayName("/ws: receive new_todo todos messages with several connections")
    fun test() {
        wsClients.forEach { it.connectBlocking() }
        "Создать несколько Todo" {
            createRequests.forEach { createTodo(it) }
        }
        wait.wait(
            reason = "Проверить наличие созданных записей в полученных сообщениях каждого клиента",
            script = { wsClients.map { it.context.getMessages<TodosWSResponse>() } },
            condition = {
                map { it.containsAll(createRequests.map { it.toTodoWsMessage() }) }.all { true }
            }
        )
    }
}
