package com.aa.weatherprediction.service

import com.aa.weatherprediction.model.City
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Unit test for API with MockServer
 */
class ConnectorServiceTest {

    val mockWebServer = MockWebServer()
    val apiService = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpClient())
        .build()
        .create(ConnectorService.ConnectorAPI::class.java)

    @Before
    fun setUp() {
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `city api should return`() {
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
        val actual: List<City> = apiService.getCity("any", "any").execute().body()!!

        // Then
        Assertions.assertEquals(actual.first().name, "Bengaluru")
        Assertions.assertEquals(actual.first().state, "Karnataka")
    }
}
// https://kotlinlang.org/docs/object-declarations.html#object-expressions
object JsonContract {
    fun readContractualJsonFile(fileName: String): String {
        val fileInputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        return fileInputStream?.bufferedReader()?.readText() ?: ""
    }
}
