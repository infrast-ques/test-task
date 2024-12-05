package api.services

import api.dto.TodosRequest
import api.implementation.rest.ApiClient
import api.implementation.rest.Request
import api.implementation.ws.WsClient
import io.restassured.http.ContentType
import listeners.threadLocalTodosIds
import org.apache.http.HttpHeaders.AUTHORIZATION
import java.net.HttpURLConnection
import java.net.URI


// todo Решил пока оставить хардкод, потом надо будет вынести в конфиг файл
private val authToken by lazy { "YWRtaW46YWRtaW4=" }
val apiClientTodos = ApiClient(requestSpecBuilder = { setBaseUri("http://localhost:8080") })

fun todosPost(data: TodosRequest) = Request(
    spec = {
        setContentType(ContentType.JSON)
        setBody(data.toString())
        threadLocalTodosIds.get().add(data.id)
    },
    send = { post("/todos") },
    statusCode = setOf(HttpURLConnection.HTTP_CREATED)
)

fun todosGet(offset: Int? = null, limit: Int? = null) = Request(
    spec = {
        offset?.let { addQueryParam("offset", it) }
        limit?.let { addQueryParam("limit", it) }
    },
    send = { get("/todos") }
)

fun todosPut(id: ULong?, data: TodosRequest?) = Request(
    spec = {
        setContentType(ContentType.JSON)
        data?.let {
            setBody(it.toString())
            if (id != data.id) {
                threadLocalTodosIds.get().remove(id)
                threadLocalTodosIds.get().add(data.id)
            }
        }
    },
    send = { put("/todos/${id ?: ""}") }
)

fun todosDelete(id: ULong?, token: String? = authToken) = Request(
    spec = {
        token?.let { addHeader(AUTHORIZATION, "Basic $it") }
        threadLocalTodosIds.get().remove(id)
    },
    send = { delete("/todos/${id ?: ""}") },
    statusCode = setOf(HttpURLConnection.HTTP_NO_CONTENT)
)

fun getTodosWsClient() = WsClient(serverUri = URI.create("ws://localhost:8080/ws"))
