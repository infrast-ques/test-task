package todos.get

import api.dto.TodosRequest
import api.dto.TodosResponseItem
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import todos.TodosGet
import utils.invoke

@TodosGet
class GetTodoWithOffsetAndLimitTest : GetTodosIsolateTest() {

    private lateinit var createRequests: List<TodosRequest>
    private lateinit var expectedResponse: List<TodosResponseItem>

    @BeforeAll
    fun setUp() {
        // Тест изолирован
        "Удалить все записи" {
            getTodos().forEach {
                deleteTodo(it.id)
            }
        }

        createRequests = List(20) { todoUtils.todoRequestData() }
        expectedResponse = createRequests.map { it.toTodoResponse() }
        "Создать ${createRequests.size} записей" {
            createRequests.forEach { createTodo(request = it) }
        }
    }

    @ParameterizedTest(name = " {0}, {1}")
    @CsvSource(
        delimiter = ',',
        textBlock = """
        0, 5
        10, 5
        15, 10"""
    )
    @DisplayName("GET /todos -> 200 ok: get todos with offset and limit")
    fun test(offset: Int, limit: Int) {
        val response = getTodos(offset = offset, limit = limit)
        "Проверить наличие созданных записей с учетом оффсета $offset и лимита $limit" {
            assertThat(response)
                .hasSize(expectedResponse.drop(offset).take(limit).size)
        }
    }
}
