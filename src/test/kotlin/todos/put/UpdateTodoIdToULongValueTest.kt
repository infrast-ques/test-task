package todos.put

import data.Consts.RESOURCE_LOCK_MAX_ULONG
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.ResourceLock
import todos.TodosBaseTest

@ResourceLock(RESOURCE_LOCK_MAX_ULONG)
class UpdateTodoIdToULongValueTest : TodosBaseTest() {

    private val createRequest = todoUtils.todoRequestData()
    private val updateRequest = todoUtils.todoRequestData().copy(id = ULong.MAX_VALUE)

    @BeforeEach
    fun setUp() {
        createTodo(request = createRequest)
    }

    @Test
    @DisplayName("PUT /todos -> 200 ok: update todo id to ULong.MAX_VALUE")
    fun test() {
        updateTodo(id = createRequest.id, request = updateRequest)
        checkTodos(updateRequest)
    }
}
