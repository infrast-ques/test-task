package utils

import io.qameta.allure.Step
import org.awaitility.core.ConditionTimeoutException
import org.awaitility.kotlin.await
import org.awaitility.kotlin.withPollDelay
import org.awaitility.kotlin.withPollInterval
import java.time.Duration

val wait = WaitUtils

object WaitUtils {

    private val defaultResolveTimeout = Duration.ofSeconds(5)
    private val defaultPollDelay = Duration.ofMillis(250)

    @Step("{reason}")
    fun <T> wait(
        timeout: Duration = defaultResolveTimeout,
        pollInterval: Duration = defaultPollDelay,
        reason: String,
        script: () -> T,
        condition: T.() -> Boolean,
    ) {
        try {
            await
                .atMost(timeout)
                .withPollDelay(pollInterval)
                .withPollInterval(pollInterval)
                .until(script, condition)
        } catch (e: ConditionTimeoutException) {
            throw Exception("Условие ожидания '$reason' не выполнено")
        }
    }


    @Step("{reason}")
    fun <T> check(
        timeout: Duration = defaultResolveTimeout,
        pollInterval: Duration = defaultPollDelay,
        reason: String,
        script: () -> T,
        condition: T.() -> Boolean,
    ): T {
        val finishTimeMillis = System.currentTimeMillis() + timeout.toMillis()
        var conditionResult: Boolean
        var result: T
        do {
            result = script()
            conditionResult = result.condition()
            Thread.sleep(pollInterval.toMillis())
        } while (conditionResult && System.currentTimeMillis() <= finishTimeMillis)

        if (!conditionResult) throw Exception("Условие ожидания '$reason' перестало выполняться")
        return result
    }
}