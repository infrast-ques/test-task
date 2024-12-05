package api.dto


enum class WSMessageTodoType(val type: String) {
    NEW_TYPE("new_todo"),
}

data class TodosWSResponse(
    val `data`: TodosRequest,
    val type: String,
)
