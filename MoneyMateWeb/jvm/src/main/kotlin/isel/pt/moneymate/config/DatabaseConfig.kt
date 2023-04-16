package isel.pt.moneymate.config

import isel.pt.moneymate.repository.CategoryRepository
import isel.pt.moneymate.repository.TransactionRepository
import isel.pt.moneymate.repository.TokensRepository
import isel.pt.moneymate.repository.UsersRepository
import isel.pt.moneymate.repository.WalletRepository
import isel.pt.moneymate.repository.mappers.CategoryMapper
import isel.pt.moneymate.repository.mappers.TokenMapper
import isel.pt.moneymate.repository.mappers.UserMapper
import isel.pt.moneymate.repository.mappers.WalletMapper
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

    @Bean
    fun tokensRepository(jdbi: Jdbi): TokensRepository {
        jdbi.registerRowMapper(TokenMapper(UserMapper()))
        return jdbi.onDemand(TokensRepository::class.java)
    }

    @Bean
    fun walletRepository(jdbi: Jdbi): WalletRepository {
        jdbi.registerRowMapper(WalletMapper(UserMapper()))
        return jdbi.onDemand(WalletRepository::class.java)
    }

    @Bean
    fun categoryRepository(jdbi: Jdbi): CategoryRepository {
        jdbi.registerRowMapper(CategoryMapper(UserMapper()))
        return jdbi.onDemand(CategoryRepository::class.java)
    }

    @Bean
    fun transactionRepository(jdbi: Jdbi): TransactionRepository {
        //here goes the mapper
        return jdbi.onDemand(TransactionRepository::class.java)
    }
}