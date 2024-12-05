package todos.delete

import listeners.threadLocalTodosIds
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import todos.TodosBaseTest
import todos.TodosDelete
import utils.invoke

@TodosDelete
class DeleteExistingTodoTest : TodosBaseTest() {

    private val createRequest = todoUtils.todoRequestData()
    private val createToDeleteRequest = todoUtils.todoRequestData()

    @BeforeEach
    fun setUp() {
        createTodo(request = createRequest)
        createTodo(request = createToDeleteRequest)
    }

    @Test
    @DisplayName("DELETE /todos -> 204 no content: delete todo")
    fun test() {
        threadLocalTodosIds
        deleteTodo(createToDeleteRequest.id)
        threadLocalTodosIds
        checkTodoNotExist(createToDeleteRequest.id)
        "Проверить, что другая запись не удалилась" {
            checkTodos(createRequest)
        }
    }
}
