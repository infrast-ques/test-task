package todos

import io.qameta.allure.Epic
import io.qameta.allure.Feature


@Epic("Todo-service")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class TodoService

@Feature("POST /todos")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class TodosPost

@Feature("PUT /todos")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class TodosPut

@Feature("GET /todos")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class TodosGet

@Feature("DELETE /todos")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class TodosDelete

@Feature("Todos WebSocket /ws")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class TodosWS

