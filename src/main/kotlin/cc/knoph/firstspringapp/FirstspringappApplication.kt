package cc.knoph.firstspringapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class FirstspringappApplication

fun main(args: Array<String>) {
    runApplication<FirstspringappApplication>(*args)
}

@RestController
class MessageResource {
    @GetMapping
    /* Function Fun return a list with Message, message is here the data class we defined */
    fun index(): List<Message> = listOf(
        Message("1", "Hello there"),
        Message("2", "Obi Wan Kenobi"),
    )
}

/*Defining a data class contain the message attributes.  Remember ? means it can be a null */
data class Message (val id: String?, val text: String)