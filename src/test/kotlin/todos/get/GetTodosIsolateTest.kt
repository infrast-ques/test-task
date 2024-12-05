package todos.get

import api.services.apiClientTodos
import api.services.todosDelete
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.parallel.Isolated
import todos.TodosBaseTest
import utils.invoke
import java.net.HttpURLConnection

@Isolated
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class GetTodosIsolateTest : TodosBaseTest() {

    // Тест изолирован
    protected fun deleteAllTodos() {
        "Удалить все записи" {
            getTodos().forEach {
                apiClientTodos.send(todosDelete(id = it.id).apply {
                    statusCode = setOf(
                        HttpURLConnection.HTTP_NO_CONTENT,
                        HttpURLConnection.HTTP_NOT_FOUND
                    )
                })
            }
        }
    }
}