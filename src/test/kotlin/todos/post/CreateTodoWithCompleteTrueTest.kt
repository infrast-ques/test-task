package todos.post

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import todos.TodosBaseTest
import todos.TodosPost

@TodosPost
class CreateTodoWithCompleteTrueTest : TodosBaseTest() {

    private val createRequest = todoUtils.todoRequestData().copy(completed = true)

    // todo Нужна ли в принципе возможность создавать уже выполненные тудушки?
    @Test
    @DisplayName("POST /todos -> 201 created: complete = true")
    fun test() {
        createTodo(request = createRequest)
        checkTodos(expectedTodo = createRequest)
    }
}
