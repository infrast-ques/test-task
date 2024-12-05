package api.dto


class TodosResponse : ArrayList<TodosResponseItem>()

data class TodosResponseItem(
    val id: ULong,
    val text: String,
    val completed: Boolean
)
