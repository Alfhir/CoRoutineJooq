package demo.eisenbarth.CoRoutineJooq

import jooq.generated.tables.pojos.Language
import jooq.generated.tables.references.LANGUAGE
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.jooq.DSLContext
import org.jooq.JSON
import org.jooq.Record1
import org.jooq.impl.DSL.jsonEntry
import org.jooq.impl.DSL.jsonObject
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
interface LanguageRepository {
    suspend fun findById(id: Int): Language?
    suspend fun findByIdJson(id: Int): Record1<JSON>?
    // TODO
    //  - Showcase multisets
}

@Repository
class LanguageRepositoryImpl(@Qualifier("r2dbcContext") private val ctx: DSLContext) : LanguageRepository {
    override suspend fun findById(id: Int): Language? = ctx
        .selectFrom(LANGUAGE)
        .where(LANGUAGE.LANGUAGE_ID.eq(id))
        .awaitFirstOrNull()
        ?.into(Language::class.java)

    override suspend fun findByIdJson(id: Int): Record1<JSON>? = ctx
        .select(
            jsonObject(
                jsonEntry("languageId", LANGUAGE.LANGUAGE_ID),
                jsonEntry("name", LANGUAGE.NAME)
            ).`as`("jsonResult")
        )
        .from(LANGUAGE)
        .where(LANGUAGE.LANGUAGE_ID.eq(id))
        .awaitFirstOrNull()
}

