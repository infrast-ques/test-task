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
class DeleteNotExistingTodoTest : TodosBaseTest() {

    // Считаем что реализация получения todoId из TodoUtils гарантирует уникальный id, которого еще нет в системе
    private val absentTodoId = todoUtils.todoRequestData().id

    @Test
    @DisplayName("DELETE /todos -> 404 not found: delete not existing todo")
    fun test() {
        "Отправить запрос на удаление несуществующей записи" {
            apiClientTodos.send(todosDelete(id = absentTodoId).apply {
                statusCode = setOf(HttpURLConnection.HTTP_NOT_FOUND)
            })
        }
    }
}
