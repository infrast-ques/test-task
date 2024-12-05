package utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule


object SerializationUtils {
    val mapper = jacksonObjectMapper().registerKotlinModule()

    inline fun <reified T> fromJson(data: String): T = mapper.readValue<T>(data)

    fun toJson(data: Any): String = mapper.writeValueAsString(data)
}