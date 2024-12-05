package todos.post

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import todos.TodosBaseTest
import todos.TodosPost

@TodosPost
class CreateTodoWithZeroIdTest : TodosBaseTest() {

    private val createRequest = todoUtils.todoRequestData()

    @Test
    @DisplayName("POST /todos -> 201 created: id = 0")
    fun test() {
        createTodo(request = createRequest)
        checkTodos(expectedTodo = createRequest)
    }
}
