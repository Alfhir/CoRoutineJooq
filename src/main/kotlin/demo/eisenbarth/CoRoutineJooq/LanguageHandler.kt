package demo.eisenbarth.CoRoutineJooq

import jooq.generated.tables.pojos.Language
import org.jooq.Record1
import org.jooq.XML
import org.springframework.http.MediaType.*
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class LanguageHandler(private val languageRepository: LanguageRepository) {
    suspend fun getLanguage(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toInt()
        return when (request.headers().firstHeader("Accept")) {
            APPLICATION_XML_VALUE -> getLanguageByIdAsXml(id)
            else -> getLanguageByIdAsDTO(id)
        }
    }

    private suspend fun getLanguageByIdAsXml(id: Int) =
        languageRepository.findByIdAsXML(id)?.xmlResponse() ?: notFoundResponse()

    private suspend fun getLanguageByIdAsDTO(id: Int) =
        languageRepository.findByIdAsDTO(id)?.jsonResponse() ?: notFoundResponse()

    private suspend fun Language.jsonResponse() =
        ok().contentType(APPLICATION_JSON).bodyValueAndAwait(this)

    private suspend fun Record1<XML>.xmlResponse() =
        ok().contentType(APPLICATION_XML).bodyValueAndAwait(this)

    private suspend fun notFoundResponse() = ServerResponse.notFound().buildAndAwait()
}
