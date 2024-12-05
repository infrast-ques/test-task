package todos.ws

import api.dto.TodosWSResponse
import api.services.getTodosWsClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import todos.TodosBaseTest
import todos.TodosWS
import utils.wait

@TodosWS
class WsCheckTodoUpdateAbsentMessageTest : TodosBaseTest() {

    private val createRequest = todoUtils.todoRequestData()
    private val updateRequest = todoUtils.todoRequestData()
    private val wsClient = getTodosWsClient()

    @BeforeEach
    fun setUp() {
        createTodo(request = createRequest)
    }

    @Test
    @DisplayName("/ws: check absent message from todos put method")
    fun test() {
        wsClient.connectBlocking()
        updateTodo(id = createRequest.id, request = updateRequest)

        wait.check(
            reason = "Проверить отсутствие информации об обновленной записи",
            script = { wsClient.context.getMessages<TodosWSResponse>() },
            condition = { none { it.data.id == updateRequest.id } }
        )
    }
}
