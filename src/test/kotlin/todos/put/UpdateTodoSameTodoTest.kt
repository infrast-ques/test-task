package todos.put

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import todos.TodosBaseTest
import todos.TodosPut

@TodosPut
class UpdateTodoSameTodoTest : TodosBaseTest() {

    private val createRequest = todoUtils.todoRequestData()
    private val updateRequest = todoUtils.todoRequestData().copy(id = createRequest.id)

    @BeforeEach
    fun setUp() {
        createTodo(request = createRequest)
    }

    @Test
    @DisplayName("PUT /todos -> 200 ok: update same todos")
    fun test() {
        updateTodo(id = createRequest.id, request = updateRequest)
        checkTodos(updateRequest)
    }
}
