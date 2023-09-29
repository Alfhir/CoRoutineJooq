package demo.eisenbarth.CoRoutineJooq

import jooq.generated.tables.pojos.Language
import org.jooq.JSONFormat
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LanguageController(
    @Qualifier("languageRepositoryImpl") val languageRepository: LanguageRepository
) {
    @GetMapping("/languages/{id}")
    @ResponseBody
    suspend fun getStock(@PathVariable("id") id: Int): Language? =
        languageRepository.findById(id)

    // If you do not want to take heavy dependencies like Jackson -
    // e.g. in a "serverless" project - JOOQ can map Results to JSON/XML in any shape.
    @GetMapping("/languages/json/{id}")
    @ResponseBody
    suspend fun getStockJson(@PathVariable("id") id: Int): ResponseEntity<String> {
        val languageJson: String? = languageRepository
            .findByIdJson(id)
            ?.formatJSON(JSONFormat.DEFAULT_FOR_RECORDS)
        return languageJson?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }
}
