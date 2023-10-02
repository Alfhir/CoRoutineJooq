package demo.eisenbarth.CoRoutineJooq

import jooq.generated.tables.pojos.Language
import jooq.generated.tables.references.LANGUAGE
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.jooq.DSLContext
import org.jooq.JSON
import org.jooq.Record1
import org.jooq.XML
import org.jooq.impl.DSL.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
open class LanguageRepository(@Qualifier("r2dbcContext") private val ctx: DSLContext){

    suspend fun findByIdAsDTO(id: Int): Language? = ctx
        .selectFrom(LANGUAGE)
        .where(LANGUAGE.LANGUAGE_ID.eq(id))
        .awaitFirstOrNull()
        ?.into(Language::class.java)

    // If you do not want to take heavy dependencies like Jackson,
    // JOOQ can map Results to JSON or XML in any shape.
    suspend fun findById(id: Int): Record1<JSON>? = ctx
        .select(
            jsonObject(
                jsonEntry("languageId", LANGUAGE.LANGUAGE_ID),
                jsonEntry("name", LANGUAGE.NAME)
            ).`as`("jsonResult")
        )
        .from(LANGUAGE)
        .where(LANGUAGE.LANGUAGE_ID.eq(id))
        .awaitFirstOrNull()

    suspend fun findByIdAsXML(id: Int): Record1<XML>? = ctx
        .select(
            xmlelement(name("language"),
                xmlattributes(),
                xmlelement(name("languageId"), LANGUAGE.LANGUAGE_ID),
                xmlelement(name("name"), LANGUAGE.NAME)
            ).`as`("xmlResult")
        )
        .from(LANGUAGE)
        .where(LANGUAGE.LANGUAGE_ID.eq(id))
        .awaitFirstOrNull()
}

