package demo.eisenbarth.CoRoutineJooq

import jooq.generated.tables.pojos.Language
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class LanguageHandler(private val languageRepository: LanguageRepository) {
    suspend fun getLanguage(request: ServerRequest) =
        getLanguageByIdAsDTO(request.pathVariable("id").toInt())

    private suspend fun getLanguageByIdAsDTO(id: Int) =
        languageRepository.findByIdAsDTO(id)?.jsonResponse() ?: notFoundResponse()

    private suspend fun Language.jsonResponse() =
        ok().contentType(APPLICATION_JSON).bodyValueAndAwait(this)

    private suspend fun notFoundResponse() = ServerResponse.notFound().buildAndAwait()
}
