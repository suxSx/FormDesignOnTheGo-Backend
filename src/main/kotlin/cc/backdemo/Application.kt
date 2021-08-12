package cc.backdemo

import com.fasterxml.jackson.databind.util.JSONPObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.json.GsonJsonParser
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.random.Random


@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@RestController
class RootController () {

    /* Here we autowire our account class and repo with late init, so it can be
    * declared after flyway f.eks have run and other options we may se in our script. */
    @Autowired
    lateinit var repository: AccountsRepository

    /* Mapping and creating end point for '/' regular GET Request */
    @GetMapping("/", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun runRootController(): ResponseEntity<Any?> {
    return ResponseEntity(Message("status", "running"), HttpStatus.OK)
    }

    /* Mapping and creating end point for '/users' Regular GET request */
    @RequestMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE], method = [RequestMethod.GET])
    @ResponseBody
    fun getUsers(): String {
        return repository.findAll().toString()
    }


    /* Mapping Create new account */
    @GetMapping("/create", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createAccount(): ResponseEntity<Any> {
        var date: String = DateTimeFormatter.ISO_INSTANT.format(Instant.now()).toString()
        var mix: String = Random.nextInt().toString()
        var kim = Accounts("kim$mix", "password", "kim$mix@gmail.com", date, date)
        repository.save(kim)

        return ResponseEntity(Message("account", "created account"), HttpStatus.OK)
    }
}


/* Data Class for Message */
data class Message(val id: String?, val text: String)

