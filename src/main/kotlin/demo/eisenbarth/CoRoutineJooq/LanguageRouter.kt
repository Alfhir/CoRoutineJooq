package demo.eisenbarth.CoRoutineJooq

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class LanguageRouter(private val handler: LanguageHandler) {
    @Bean
    fun routes() = coRouter {
        GET("/languages/{id}", accept(APPLICATION_JSON), handler::getLanguage)
    }

}