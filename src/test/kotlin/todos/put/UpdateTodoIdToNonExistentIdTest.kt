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
class UpdateTodoIdToNonExistentIdTest : TodosBaseTest() {

    // Считаем что реализация получения todoId из TodoUtils гарантирует уникальный id, которого еще нет в системе
    private val notExistentId = todoUtils.todoRequestData().id
    private val updateRequest = todoUtils.todoRequestData()

    @Test
    @DisplayName("PUT /todos -> 404 not found: update record id to non existent id")
    fun test() {
        "Обновить несуществующую запись" {
            apiClientTodos.send(todosPut(id = notExistentId, data = updateRequest).apply {
                statusCode = setOf(HttpURLConnection.HTTP_NOT_FOUND)
            })
        }
    }
}
