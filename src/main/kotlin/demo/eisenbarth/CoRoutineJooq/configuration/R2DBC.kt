package demo.eisenbarth.CoRoutineJooq.configuration

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class R2DBC(
    @Value("\${spring.r2dbc.url}") val url: String,
    @Value("\${spring.r2dbc.username}") val username: String,
    @Value("\${spring.r2dbc.password}") val password: String
) {
    @Bean
    fun r2dbcConnectionFactory(): ConnectionFactory = ConnectionFactories.get(
        ConnectionFactoryOptions.parse(url)
            .mutate()
            .option(ConnectionFactoryOptions.USER, username)
            .option(ConnectionFactoryOptions.PASSWORD, password)
            .build()
    )

    @Bean
    fun r2dbcContext(
        connectionFactory: ConnectionFactory
    ): DSLContext = DSL.using(connectionFactory)
}