package todos.put

import api.services.apiClientTodos
import api.services.todosPut
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import todos.TodosBaseTest
import todos.TodosPut
import utils.invoke
import java.net.HttpURLConnection

@TodosPut
class UpdateTodoWithoutEndpointIdTest : TodosBaseTest() {

    private val updateRequest = todoUtils.todoRequestData()

    @Test
    @DisplayName("PUT /todos -> 405 method not allowed: update without todo id in the endpoint")
    fun test() {
        "Вызвать метод без указания id записи в эндпоинте" {
            apiClientTodos.send(todosPut(id = null, data = updateRequest).apply {
                statusCode = setOf(HttpURLConnection.HTTP_BAD_METHOD)
            })
        }
    }
}
