package api.implementation.rest

import io.qameta.allure.restassured.AllureRestAssured
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.response.Response
import io.restassured.specification.ResponseSpecification
import org.hamcrest.Matchers

class ApiClient(
    val requestSpecBuilder: RequestSpecBuilder.() -> Unit = {},
    val responseSpecBuilder: ResponseSpecification.() -> Unit = {},
) {

    fun send(request: Request): Response {
        val requestSpec = RestAssured.given()
            .filter(AllureRestAssured())
            .spec(
                RequestSpecBuilder().apply {
                    requestSpecBuilder(this)
                    request.spec(this)
                }.build()
            ).log().all()

        val responseSpec = ResponseSpecBuilder().apply {
            responseSpecBuilder
            expectStatusCode(Matchers.`in`(request.statusCode))
        }.build()

        val requestSender = RestAssured.given(requestSpec, responseSpec)
        val response = request.send(requestSender).then().log().all().extract().response()

        return response
    }
}
