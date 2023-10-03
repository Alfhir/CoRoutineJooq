package demo.eisenbarth.CoRoutineJooq

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Qualifier

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class ApplicationRunner(@Qualifier("jdbcContext") private val jdbcContext: DSLContext) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        printTableInformation()
    }

    private fun printTableInformation(): Unit = jdbcContext.meta().tables.forEach { table -> println("Table: ${table.name}") }
}
