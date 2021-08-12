package cc.backdemo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


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
    fun getUsers(): MutableIterable<Accounts> {
        return repository.findAll()
    }

    /* Mapping and creating end point for '/users' Regular GET request */
    @GetMapping("/error", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun pageNotFound(): ResponseEntity<Any> {
        return ResponseEntity(Message("ERROR", "NOPE"), HttpStatus.OK)
    }

}


/* Data Class for Message */
data class Message(val id: String?, val text: String)

