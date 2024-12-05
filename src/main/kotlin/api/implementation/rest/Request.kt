package api.implementation.rest

import io.restassured.builder.RequestSpecBuilder
import io.restassured.response.Response
import io.restassured.specification.RequestSender
import java.net.HttpURLConnection

class Request(
    val spec: RequestSpecBuilder.() -> Unit = {},
    val send: RequestSender.() -> Response,
    var statusCode: Set<Int> = setOf(
        HttpURLConnection.HTTP_OK,
        HttpURLConnection.HTTP_CREATED
    )
)
