package api.dto

import utils.SerializationUtils

data class TodosRequest(
    val id: ULong,
    val text: String,
    val completed: Boolean
) {

    override fun toString() = SerializationUtils.toJson(this)

    fun toTodoResponse() = TodosResponseItem(
        id = id,
        text = text,
        completed = completed
    )

    fun toTodoWsMessage() = TodosWSResponse(
        `data` = this,
        type = WSMessageTodoType.NEW_TYPE.type
    )
}
