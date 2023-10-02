package demo.eisenbarth.CoRoutineJooq

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_XML
import org.springframework.web.reactive.function.server.CoRouterFunctionDsl
import org.springframework.web.reactive.function.server.coRouter

@Configuration
open class LanguageRouter(private val handler: LanguageHandler) {
    @Bean
    open fun router() = coRouter {
        acceptJsonOrXml().nest {
            GET("/languages/{id}", handler::getLanguage)
        }
    }

    private fun CoRouterFunctionDsl.acceptJsonOrXml() =
        accept(APPLICATION_JSON) or accept(APPLICATION_XML)
}