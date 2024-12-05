package utils

import org.apache.commons.lang3.RandomStringUtils

val stringUtils: RandomStringUtils by lazy { RandomStringUtils.insecure() }