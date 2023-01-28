package com.aa.weatherprediction.resource

import com.aa.weatherprediction.model.Report
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import redis.embedded.RedisServer


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.data.redis.url=redis://localhost:6379"
    ]
)
//@ExtendWith(SpringExtension::class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ForecastResourceTests @Autowired constructor(
    private val restTemplate: TestRestTemplate
){

    val name = "delhi"

    @LocalServerPort
    private var port: Int = 0

    val redisServer = RedisServer.builder().setting("bind localhost").port(6379).build()
    val mockWebServer = MockWebServer()
    @Before
    fun setUp() {
        redisServer.start()
        mockWebServer.start(port)
    }
    @After
    fun tearDown() {
        mockWebServer.shutdown()
        redisServer.stop()
    }

    @Test
    fun `report should return`() {
        // Given
        val cityContract = JsonContract.readContractualJsonFile("city-payload-open-weather-api.json")
        val cityResponse = MockResponse()
            .setResponseCode(HttpStatus.OK.value())
            .setBody(cityContract)
        val weatherContract = JsonContract.readContractualJsonFile("weather-payload-open-weather-api.json")
        val weatherResponse = MockResponse()
            .setResponseCode(HttpStatus.OK.value())
            .setBody(weatherContract)
        mockWebServer.enqueue(cityResponse)
        mockWebServer.enqueue(weatherResponse)

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
// https://kotlinlang.org/docs/object-declarations.html#object-expressions
object JsonContract {
    fun readContractualJsonFile(fileName: String): String {
        val fileInputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        return fileInputStream?.bufferedReader()?.readText() ?: ""
    }
}
