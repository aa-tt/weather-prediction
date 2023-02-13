package com.aa.weatherprediction.model

import com.squareup.moshi.JsonClass
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Serializer
import org.slf4j.LoggerFactory


@JsonClass(generateAdapter = true)
data class UsagePrice(
    var userId: String?,
    var event: EVENT?,
) {constructor(): this(null,null)}

enum class EVENT {
    ORDER, FAILURE, SUCCESS
}

class UsagePriceSerializer : Serializer<UsagePrice> {
    private val objectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)

    override fun serialize(topic: String?, data: UsagePrice?): ByteArray? {
        log.info("Serializing...")
        return objectMapper.writeValueAsBytes(
            data ?: throw SerializationException("Error when serializing UsagePrice to ByteArray[]")
        )
    }

    override fun close() {}
}
