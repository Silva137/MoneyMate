package isel.pt.moneymate.config

import isel.pt.moneymate.repository.UsersRepository
import isel.pt.moneymate.repository.mappers.UserMapper
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource


@Configuration
class DatabaseConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun driverManagerDataSource(): DataSource {
        return DriverManagerDataSource()
    }

    @Bean
    fun dataSourceTransactionManager(dataSource: DataSource?): DataSourceTransactionManager {
        val dataSourceTransactionManager = DataSourceTransactionManager()
        dataSourceTransactionManager.dataSource = dataSource
        return dataSourceTransactionManager
    }

    @Bean
    fun jdbi(dataSource: DataSource?): Jdbi {
        return Jdbi.create(dataSource)
            .installPlugin(SqlObjectPlugin())
            .installPlugin(PostgresPlugin())
    }

    @Bean
    fun usersRepository(jdbi: Jdbi): UsersRepository {
        jdbi.registerRowMapper(UserMapper())
        return jdbi.onDemand(UsersRepository::class.java)
    }
}