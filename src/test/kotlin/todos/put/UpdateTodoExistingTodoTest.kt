package todos.put

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import todos.TodosBaseTest
import todos.TodosPut
import utils.invoke

@TodosPut
class UpdateTodoExistingTodoTest : TodosBaseTest() {

    private val createRequest = todoUtils.todoRequestData().copy(completed = false)
    private val updateRequest = todoUtils.todoRequestData().copy(completed = true)

    @BeforeEach
    fun setUp() {
        createTodo(request = createRequest)
    }

    @Test
    @DisplayName("PUT /todos -> 200 ok: update completed field (false -> true) an existing todo")
    fun test() {
        updateTodo(id = createRequest.id, request = updateRequest)
        checkTodos(updateRequest)
        "Проверить, что запись с id = ${createRequest.id} отсутствует в списке" {
            assertThrows<NoSuchElementException> { getTodos().first { it.id == createRequest.id } }
        }
        "Вернуть у записи старый id" {
            updateTodo(id = updateRequest.id, request = updateRequest.copy(id = createRequest.id))
        }
        checkTodos(updateRequest.copy(id = createRequest.id))
    }
}
