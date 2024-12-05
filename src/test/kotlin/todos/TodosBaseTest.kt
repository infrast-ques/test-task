package todos

import api.dto.TodosRequest
import api.dto.TodosResponse
import api.services.TodoUtils
import api.services.apiClientTodos
import api.services.todosDelete
import api.services.todosGet
import api.services.todosPost
import api.services.todosPut
import io.qameta.allure.Step
import io.restassured.response.Response
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.assertThrows
import utils.SerializationUtils
import utils.invoke

@TodoService
abstract class TodosBaseTest {
    val todoUtils = TodoUtils
    private fun Response.todosDeserialize() = SerializationUtils.fromJson<TodosResponse>(asString())


    @Step("Получить todo")
    fun getTodos(offset: Int? = null, limit: Int? = null): TodosResponse =
        apiClientTodos.send(todosGet(offset = offset, limit = limit)).todosDeserialize()


    @Step("Создать todo")
    fun createTodo(request: TodosRequest) {
        apiClientTodos.send(todosPost(data = request))
    }

    @Step("Обновить todo")
    fun updateTodo(id: ULong, request: TodosRequest) {
        apiClientTodos.send(todosPut(id = id, data = request))
    }

    @Step("Удалить todo")
    fun deleteTodo(id: ULong) {
        apiClientTodos.send(todosDelete(id = id))
    }


    @Step("Проверить данные todo {expectedTodo.id}")
    fun checkTodos(expectedTodo: TodosRequest) {
        val actualTodo = "Получить созданную todo id = ${expectedTodo.id}" {
            getTodos().firstOrNull { it.id == expectedTodo.id }
                ?: throw NoSuchElementException("Todo with id = ${expectedTodo.id} not found")
        }

        SoftAssertions.assertSoftly {
            it.assertThat(actualTodo.id)
                .describedAs("Проверить поле id")
                .isEqualTo(expectedTodo.id)

            it.assertThat(actualTodo.text)
                .describedAs("Проверить поле text")
                .isEqualTo(expectedTodo.text)

            it.assertThat(actualTodo.completed)
                .describedAs("Проверить поле completed")
                .isEqualTo(expectedTodo.completed)
        }
    }

    fun checkTodoNotExist(id: ULong) {
        "Проверить, что запись с id = $id отсутствует в списке" {
            assertThrows<NoSuchElementException> { getTodos().first { it.id == id } }
        }
    }
}
