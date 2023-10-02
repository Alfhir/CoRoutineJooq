package demo.eisenbarth.CoRoutineJooq

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.ServerRequest

class LanguageHandlerTests {

    private val repository = mockk<LanguageRepository>()

    private val handler = LanguageHandler(repository)
    private val router = LanguageRouter(handler)

    private val webClient: WebTestClient = WebTestClient
        .bindToRouterFunction(router.routes())
        .build()

    private val mockServerRequest = mockk<ServerRequest>() {
        every { pathVariable("id") } returns "1"
    }


    @Test
    fun `getLanguage should call findByIdAsDTO when Accept header is APPLICATION_JSON_VALUE`() = runBlocking {
        every { mockServerRequest.headers().firstHeader("Accept") } returns APPLICATION_JSON_VALUE
        val languageDtoMock = jooq.generated.tables.pojos.Language(1, "English")
        coEvery { repository.findByIdAsDTO(1) } returns languageDtoMock

        webClient.get().uri("/languages/{id}", 1)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(APPLICATION_JSON)
            .expectBody().json("""{"languageId":1,"name":"English"}""")

        coVerify { repository.findByIdAsDTO(1) }
    }

    @Test
    fun `getLanguage should return not found when language is null`() = runBlocking {
        every { mockServerRequest.headers().firstHeader("Accept") } returns APPLICATION_JSON_VALUE
        coEvery { repository.findByIdAsDTO(1) } returns null
        val response = handler.getLanguage(mockServerRequest)
        assertEquals(HttpStatusCode.valueOf(404), response.statusCode())
    }

}