package todos.get

import org.junit.jupiter.api.TestInstance
import todos.TodosBaseTest


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class GetTodosIsolateTest : TodosBaseTest()