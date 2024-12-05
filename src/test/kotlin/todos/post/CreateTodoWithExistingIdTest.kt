package todos.post

import api.services.apiClientTodos
import api.services.todosPost
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import todos.TodosBaseTest
import todos.TodosPost
import utils.invoke
import java.net.HttpURLConnection

@TodosPost
class CreateTodoWithExistingIdTest : TodosBaseTest() {

    private val createRequest = todoUtils.todoRequestData()

    @BeforeEach
    fun setUp() {
        createTodo(request = createRequest)
    }

    @Test
    @DisplayName("POST /todos -> 400 Bad Request: create todo with existing id")
    fun test() {
        "Отправить запрос на создание записей с занятым id" {
            apiClientTodos.send(todosPost(data = createRequest).apply {
                statusCode = setOf(HttpURLConnection.HTTP_BAD_REQUEST)
            })
        }
    }
}
