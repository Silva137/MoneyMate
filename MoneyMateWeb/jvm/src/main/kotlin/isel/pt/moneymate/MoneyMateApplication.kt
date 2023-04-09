package isel.pt.moneymate

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MoneyMateApplication

private val log = LoggerFactory.getLogger("main")

fun main(args: Array<String>) {
    log.info("::STARTING::")
    runApplication<MoneyMateApplication>(*args)
}
