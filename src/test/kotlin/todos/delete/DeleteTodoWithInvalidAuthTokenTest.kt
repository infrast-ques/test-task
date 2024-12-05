package todos.delete

import api.services.apiClientTodos
import api.services.todosDelete
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import todos.TodosBaseTest
import todos.TodosDelete
import utils.invoke
import utils.stringUtils
import java.net.HttpURLConnection

@TodosDelete
class DeleteTodoWithInvalidAuthTokenTest : TodosBaseTest() {

    private val createRequest = todoUtils.todoRequestData()

    @BeforeEach
    fun setUp() {
        createTodo(request = createRequest)
    }

    @Test
    @DisplayName("DELETE /todos -> 401 unauthorized: delete with invalid auth token")
    fun test() {
        "Отправить запрос на удаление записи c неверным паролем" {
            apiClientTodos.send(
                todosDelete(
                    id = createRequest.id,
                    token = stringUtils.nextAlphabetic(15)
                ).apply {
                    statusCode = setOf(HttpURLConnection.HTTP_UNAUTHORIZED)
                })
        }

    }
}
