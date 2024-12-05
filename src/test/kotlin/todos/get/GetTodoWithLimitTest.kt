package todos.get

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import todos.TodosGet
import utils.invoke

@TodosGet
class GetTodoWithLimitTest : GetTodosIsolateTest() {

    @BeforeAll
    fun setUp() {
        "Создать 20 записей" {
            repeat(20) { createTodo(request = todoUtils.todoRequestData()) }
        }
    }

    @ParameterizedTest(name = " {0}")
    @CsvSource(
        delimiter = ',',
        textBlock = """
        0
        10
        20"""
    )
    @DisplayName("GET /todos -> 200 ok: get todos with limit")
    fun test(limit: Int) {
        val response = getTodos(limit = limit)
        "Проверить наличие созданных записей с учетом лимита $limit" {
            assertThat(response)
                .hasSize(limit)
        }
    }
}
