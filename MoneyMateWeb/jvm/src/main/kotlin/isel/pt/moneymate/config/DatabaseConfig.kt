package isel.pt.moneymate.config

import isel.pt.moneymate.repository.*
import isel.pt.moneymate.repository.mappers.*
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
        val userMapper = UserMapper()
        val categoryMapper = CategoryMapper(userMapper)
        val walletMapper = WalletMapper(userMapper)
        val transactionMapper = TransactionMapper(userMapper, categoryMapper, walletMapper)

        val walletBalanceMapper = WalletBalanceMapper()
        val categoyBalanceMapper = CategoryBalanceMapper(categoryMapper)
        val userBalanceMapper = UserBalanceMapper(userMapper)

        jdbi.registerRowMapper(transactionMapper)
        jdbi.registerRowMapper(walletBalanceMapper)
        jdbi.registerRowMapper(categoyBalanceMapper)
        jdbi.registerRowMapper(userBalanceMapper)

        return jdbi.onDemand(TransactionRepository::class.java)
    }

    @Bean
    fun inviteRepository(jdbi: Jdbi): InviteRepository {
        val userMapper = UserMapper()
        val userMapper1 = UserMapperWithParams("user_id_1", "username1", "email1", "password1")
        val userMapper2 = UserMapperWithParams("user_id_2", "username2", "email2", "password2")
        val walletMapper = WalletMapper(userMapper)


        jdbi.registerRowMapper(InviteMapper(userMapper1, userMapper2, walletMapper))
        return jdbi.onDemand(InviteRepository::class.java)
    }


    @Bean
    fun regularTransactionRepository(jdbi: Jdbi): RegularTransactionRepository {
        val userMapper = UserMapper()
        val categoryMapper = CategoryMapper(userMapper)
        val walletMapper = WalletMapper(userMapper)
        val transactionMapper = TransactionMapper(userMapper, categoryMapper, walletMapper)
        val regularTransactionMapper = RegularTransactionMapper (transactionMapper)
        jdbi.registerRowMapper(regularTransactionMapper)
        return jdbi.onDemand(RegularTransactionRepository::class.java)
    }
}