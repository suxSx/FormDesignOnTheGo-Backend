package cc.backdemo

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeFormatter



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
    fun runRootController(): ResponseEntity<Any> {
    return ResponseEntity(Gson().toJson(Message("OK", "Backend Test Server for Learning Purpose")), HttpStatus.OK)
    }

    /* Mapping and creating end point for '/users' Regular GET request
    *  Return a JSON List of users without password */
    @RequestMapping("/users", method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    /* Return as a string here for testing and difference between string and ResponseEntity */
    fun getUsers(): String {
        /* Mapping userdata and removing extra data such as password and displaying it. */
        val mappedUserList = repository.findAll().map {accounts: Accounts? -> accounts?.getBasic() ?: "" }
        return Gson().toJson(mappedUserList)
    }

    /* Mapping for single user info, using PATH Variable to get user id from URL Path.  */
    @RequestMapping("/users/{id}", method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getUserByID(@PathVariable(value = "id") userid: String): ResponseEntity<Any> {
        /* Check if Int / Valid User ID */
        if(!userid.isInt()) { return ResponseEntity(returnError("Invalid user id, check syntax"), HttpStatus.OK) }

        /* Get User Data*/
        val userData = repository.findBasicUserDataByID(userid.toInt())

        /* Check if return was empty incase no users */
        if(userData.isEmpty()) {
            return ResponseEntity(returnOK("No user found with that ID"), HttpStatus.OK)
        }

        /* Creating JSON Object */
        val userDetails = JsonObject()

        /* Mapping Result adding row name and value to JSONObject before pushing it*/
        for(row in userData[0].elements) { userDetails.addProperty(row.alias.toString(), userData[0].get(row).toString()) }

        /* Return User Data */
        return ResponseEntity(userDetails.toString(), HttpStatus.OK)
    }

    /* Mapping Create new account */
    @RequestMapping("/add-user", method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createAccount(@RequestBody request: String): ResponseEntity<Any> {
        /* Convert The JSON Object to Data Class.
        * If the values do not match the data class, the values in the data class wil be set to NULL. */
        val userData = Gson().fromJson(request, NewUser::class.java)
        val date: String = DateTimeFormatter.ISO_INSTANT.format(Instant.now()).toString()

        /* Checking if userData Contains NULL */
        if(userData.toString().fromStringIsAnyNull()) { return ResponseEntity(returnError("Invalid JSON, check your syntax and try once more."), HttpStatus.OK) }

        /* Running a Check to see if it all ready exist, thats the username/email. If so return the message to user. */
        var userID = repository.findUserIdByUsername(userData.username)
        if(userID != null) { return ResponseEntity(returnError("Username is taken, try another"), HttpStatus.OK) }

        /* Checking if email all ready exist */
        val userEmail = repository.findUserIdByEmail(userData.email)
        if(userEmail != null) { return ResponseEntity(returnError("Email is taken, try another"), HttpStatus.OK) }

        /* If All Done Correct we can try to add it to data-base */
        val newUser = Accounts(userData.username, userData.password, userData.email, date, date)
        repository.save(newUser)

        /* Return user_id for the new account */
        userID = repository.findUserIdByUsername(userData.username)
        return returnNewUser(userID, userData)
    }
}

/* Data Classes for intern use */
data class Message(val status: String?, val message: String)
data class NewUser(val username:String, val password: String, val email: String)

/* Is Null in data class */
fun String.fromStringIsAnyNull(): Boolean {
    /* Mapping values out from String */
    val mappedString =  this.substringAfter('(')
                            .substringBefore(')')
                            .split(",")
                            .map { it.split("=")[1] }

    /* Checking if it contains null */
    for(x in mappedString) {
        if(x == "null") {
            /* Null found return true */
            return true
        }
    }

    /* No Null Found return false*/
    return false
}

/* Fun Return Error Message */
fun returnError(message: String): String {
    return Message("ERROR", message).returnJSON()
}

/* Fun Return OK Message */
fun returnOK(message: String): String {
    return Message("OK", message).returnJSON()
}

/* Return JSON Func */
fun Any.returnJSON(): String {
    return Gson().toJson(this)
}

/* Check if String Can be Int */
fun String.isInt(): Boolean {
    return if(this.isNullOrEmpty()) false else this.all { Character.isDigit(it) }
}

/* Return New User Data */
fun returnNewUser(userid: String?, userdata: NewUser): ResponseEntity<Any> {
    val returnObject = JsonObject()
    returnObject.addProperty("userid", userid)
    returnObject.addProperty("username", userdata.username)
    returnObject.addProperty("email", userdata.email)

    return ResponseEntity(returnObject.toString(), HttpStatus.OK)
}

