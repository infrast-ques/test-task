package todos.post

import data.Consts.RESOURCE_LOCK_MAX_ULONG
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.ResourceLock
import todos.TodosBaseTest
import todos.TodosPost

@TodosPost
@ResourceLock(RESOURCE_LOCK_MAX_ULONG)
class CreateTodoWithMaxIdTest : TodosBaseTest() {

    private val createRequest = todoUtils.todoRequestData().copy(id = ULong.MAX_VALUE)

    @Test
    @DisplayName("POST /todos -> 201 created: id = ULong.MAX_VALUE")
    fun test() {
        createTodo(request = createRequest)
        checkTodos(expectedTodo = createRequest)
    }
}
