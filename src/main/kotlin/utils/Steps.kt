package utils

import io.qameta.allure.Step

inline operator fun <T> String.invoke(crossinline block: () -> T): T {
    return invokeStep(this, block)
}

@Step("{stepName}")
@Suppress("UNUSED_PARAMETER")
inline fun <T> invokeStep(stepName: String, crossinline script: () -> T): T = script()
