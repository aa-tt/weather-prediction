package com.aa.weatherprediction.resource

import com.aa.weatherprediction.model.Report
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import redis.embedded.RedisServer


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.data.redis.url=redis://localhost:6379",
        "spring.cloud.consul.host=localhost",
        "spring.cloud.consul.port=8500",
        "cors.originPatterns=",
    ]
)
class ForecastResourceTests @Autowired constructor(
    private val restTemplate: TestRestTemplate
){

    @LocalServerPort
    private var port: Int = 0

    val redisServer = RedisServer.builder().setting("bind localhost").port(6379).build()

    @Before
    fun setUp() {
        redisServer.start()
    }
    @After
    fun tearDown() {
        redisServer.stop()
    }

    @Test
    fun `report should return`() {
        // Given
        val name = "Delhi"

        // When
        val actual = restTemplate.getForEntity(
            "http://localhost:$port/cities/$name/forecast",
            Report::class.java
        )

        // Then
        print(actual)

        Assertions.assertEquals(200, actual.statusCode.value())
        Assertions.assertNotNull(actual.body)
    }
}
