package cc.knoph.firstspringapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class FirstspringappApplication

fun main(args: Array<String>) {
    runApplication<FirstspringappApplication>(*args)
}

@RestController
class MessageResource {
    fun index():
}

