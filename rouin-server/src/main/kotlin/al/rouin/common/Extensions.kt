package al.rouin.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass


fun <T : Any> T.logger(): Logger = LoggerFactory.getLogger(this::class.java)


private val OBJECT_MAPPER = ObjectMapper()
    .registerModule(kotlinModule())
    .registerModule(JavaTimeModule())
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)


fun <T : Any> T.toJson(): String = OBJECT_MAPPER.writeValueAsString(this) //TODO error handling
fun <T : Any> String.toObject(kClass: KClass<T>): T = OBJECT_MAPPER.readValue(this, kClass.java) //TODO error handling

fun <T : Any> T?.isNull(): Boolean = this == null
fun <T : Any> T?.isNotNull(): Boolean = !this.isNull()
