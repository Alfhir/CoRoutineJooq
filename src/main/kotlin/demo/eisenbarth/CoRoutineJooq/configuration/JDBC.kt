package demo.eisenbarth.CoRoutineJooq.configuration

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource

@Configuration
class JDBC(
    @Value("\${spring.jdbc.url}") val databaseUrl: String,
    @Value("\${spring.jdbc.username}") val name: String,
    @Value("\${spring.jdbc.password}") val pwd: String
) {
    @Bean
    fun jdbcContext(): DSLContext = DSL.using(
        DriverManagerDataSource().apply {
            url = databaseUrl
            username = name
            password = pwd
        }, SQLDialect.POSTGRES
    )
}