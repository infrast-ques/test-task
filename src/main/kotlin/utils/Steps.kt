package utils

import io.qameta.allure.Allure

operator fun <T> String.invoke(block: () -> T): T = Allure.step(this, block)
