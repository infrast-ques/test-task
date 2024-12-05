package todos.get

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import todos.TodosBaseTest
import todos.TodosGet
import utils.invoke

@TodosGet
class GetTodoWithoutParamsTest : TodosBaseTest() {

    private val createRequests = List(20) { todoUtils.todoRequestData() }

    @BeforeEach
    fun setUp() {
        // Тест изолирован
        "Удалить все записи" {
            getTodos().forEach {
                deleteTodo(it.id)
            }
        }

        "Создать ${createRequests.size} записей" {
            createRequests.forEach { createTodo(request = it) }
        }
    }

    @Test
    @DisplayName("GET /todos -> 200 ok: get todos without params")
    fun test() {
        val response = getTodos()
        "Проверить наличие всех созданных записей" {
            assertThat(response)
                .containsExactlyInAnyOrderElementsOf(createRequests.map { it.toTodoResponse() })
        }
    }
}
