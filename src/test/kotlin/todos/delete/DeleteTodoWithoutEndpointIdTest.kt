package todos.delete

import api.services.apiClientTodos
import api.services.todosDelete
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import todos.TodosBaseTest
import todos.TodosDelete
import utils.invoke
import java.net.HttpURLConnection

@TodosDelete
class DeleteTodoWithoutEndpointIdTest : TodosBaseTest() {

    @Test
    @DisplayName("DELETE /todos -> 405 method not allowed: delete without todo id in the endpoint")
    fun test() {
        "Отправить запрос на записи без указания id" {
            apiClientTodos.send(todosDelete(id = null).apply {
                statusCode = setOf(HttpURLConnection.HTTP_BAD_METHOD)
            })
        }
    }
}
