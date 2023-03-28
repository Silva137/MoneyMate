package isel.pt.moneymate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("isel.pt.moneymate.repository")
class MoneyMateApplication

fun main(args: Array<String>) {
    runApplication<MoneyMateApplication>(*args)
}
