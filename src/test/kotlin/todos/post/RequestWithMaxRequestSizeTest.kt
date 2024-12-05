package todos.post

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import todos.TodosBaseTest
import todos.TodosPost
import utils.stringUtils

@TodosPost
class RequestWithMaxRequestSizeTest : TodosBaseTest() {

    private val createRequest = todoUtils.todoRequestData().copy(text = stringUtils.nextAlphabetic(16000))

    @Test
    @DisplayName("POST /todos -> 201 created: request size <= 16kb")
    fun test() {
        createTodo(request = createRequest)
        checkTodos(expectedTodo = createRequest)
    }
}
