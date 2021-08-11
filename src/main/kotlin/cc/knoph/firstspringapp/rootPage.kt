package cc.knoph.firstspringapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ResponseBody

@SpringBootApplication
class RootPage

fun main(args: Array<String>) {
    runApplication<RootPage>(*args)
}

@RestController
class RootController () {
    /* Mapping and creating end point for '/' regular GET Request */
    @GetMapping("/", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun runRootController(): ResponseEntity<Any?> {
    return ResponseEntity(Message("status", "running"), HttpStatus.OK)
    }

    /* Mapping and creating end point for '/users' Regular GET request */
    @GetMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getUserData(): ResponseEntity<Any> {
        //TODO(GET POST REQUEST)
        return ResponseEntity(Message("users", "no user data found"), HttpStatus.OK)
    }
}


/* Data Class for Message */
data class Message(val id: String?, val text: String)

