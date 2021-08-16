package cc.backdemo

import cc.backdemo.module.account.Accounts
import cc.backdemo.module.account.AccountsRepository
import cc.backdemo.module.forms.*
import com.google.gson.Gson
import com.google.gson.JsonArray
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

    /* Init Repo for formsTable */
    @Autowired
    lateinit var formRepo: FormsRepository

    /* Init Repo for formsInfo */
    @Autowired
    lateinit var formInfoHead: FormInfoRepository

    /* Init Repo for formsInfo */
    @Autowired
    lateinit var formInfoItems: FormInfoItemsRepository

    /* Init Repo for formItems*/
    @Autowired
    lateinit var formItemRepo: FormItemsRepository

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

    /* Mapping for getting all Form data: /forms - GET
     * Return Value Should be in JSON: {"form_id":"-Mg_KH0ykhXa661dlVXo", "form_time":"some_time_stamp"*/
    @RequestMapping("/forms", method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])

    /* Enabeling CORS for localhosting */
    @CrossOrigin(origins = ["http://localhost:3000", "http://127.0.0.1:3000", "http://127.0.0.1:80", "http://127.0.0.1:8080"])

    /* Respons body for the /form */
    @ResponseBody
    fun sendFormID(): String {
        /* Mapping userdata and removing extra data such as password and displaying it. */
        val allForms = formRepo.findAll()

        return Gson().toJson(allForms)
    }

    /* Mapping for adding form data /forms - POST */
    /* JSON THAT WILL BE RECIVED:
    * { "formItems":[{"id":0,"type":"text","title":"Text Field","description":"Som Decription","needed":"--OK--","options":"","rank":0,"validation":"empty","error":"no need for error"},{"id":1,"type":"button","title":"Submit the form","description":"","needed":"","options":"","rank":1,"validation":"button"}],
    *   "formInfo":{"title":"Super tile","subtitle":"Sub Stutle","info":[{"id":1,"type":"Address","text":"","icon":"pos","action":"","error":"Enter a valid Adress only letter and number"},{"id":2,"type":"Phone","text":"","icon":"phone","action":"","error":"Enter a valid Phone only letter and number"},{"id":3,"type":"Email","text":"","icon":"email","action":"","error":"Enter a valid Email remember the @"},{"id":4,"type":"Social","text":"","icon":"corpo","action":"","error":"Enter a valid social address only letter and number"}]},
    *   "formTime":"8/15/2021, 4:59:47 PM"}*/
    @RequestMapping("/forms", method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])

    /* Enabeling CORS for localhosting */
    @CrossOrigin(origins = ["http://localhost:3000", "http://127.0.0.1:3000", "http://127.0.0.1:80", "http://127.0.0.1:8080"])

    /* Response body for POST /forms */
    @ResponseBody
    fun addNewForm(@RequestBody request: String): ResponseEntity<Any> {
        /* Convert The JSON Object to Data Class.
        * If the values do not match the data class, the values in the data class wil be set to NULL. */
        val userFormData = Gson().fromJson(request, NewFormData::class.java)

        /* Insert Time Into table: forms - Get New Form ID from table: forms or new form class */
        val newForm = Forms(userFormData.formTime)
        formRepo.save(newForm)

        /* Insert form_id, title, subtitle into table: form_info -  Get new form_info_id with sql or class */
        val newFormInfo = FormInfo(newForm.form_id, userFormData.formInfo.title.removeQuote(), userFormData.formInfo.subtitle.removeQuote());
        formInfoHead.save(newFormInfo)

        /* Insert info objects into table: info */
        for(x in userFormData.formInfo.info) {
            x.form_info_id = newFormInfo.form_info_id
            formInfoItems.save(x)
        }

        /* Insert form items into table: form_items*/
        for(x in userFormData.formItems) {
            x.form_id = newForm.form_id
            formItemRepo.save(x)
        }

        /* Return new form id*/
        return ResponseEntity(Message("post-request", newForm.form_id.toString()), HttpStatus.OK)
    }

    /* Mapping for getting a specific form data /forms/{formid}
    * Return in JSON:
    * { "formInfo":{"info":[{"action":"","icon":"pos","id":1,"text":"","type":"Address"},{"action":"","icon":"phone","id":2,"text":"","type":"Phone"},{"action":"","icon":"email","id":3,"text":"","type":"Email"},{"action":"","icon":"corpo","id":4,"text":"","type":"Social"}],"title":"Super Title","subtitle":""},
    *   "formItems":[{"description":"","error":"","id":0,"needed":0,"options":"","rank":0,"title":"","type":"text","validation":"empty"},{"description":"","error":"","id":1,"needed":0,"options":"","rank":1,"title":"","type":"button","validation":"button"},{"description":"","error":"","id":2,"needed":0,"options":"","rank":2,"title":"","type":"textarea","validation":"empty"},{"description":"","error":"","id":3,"needed":0,"options":"","rank":3,"title":"","type":"button","validation":"button"},{"description":"","error":"","id":4,"needed":0,"options":"","rank":4,"title":"","type":"text","validation":"empty"},{"description":"","error":"","id":5,"needed":0,"options":"","rank":5,"title":"","type":"textarea","validation":"empty"},{"description":"","error":"","id":6,"needed":0,"options":"","rank":6,"title":"","type":"button","validation":"button"}],
    *   "formTime":"8/8/2021, 12:35:40 PM"} */
    @RequestMapping("forms/{form_id}", method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])

    /* Enabeling CORS for localhosting */
    @CrossOrigin(origins = ["http://localhost:3000", "http://127.0.0.1:3000", "http://127.0.0.1:80", "http://127.0.0.1:8080"])

    /* Respons body for the /forms/{id} */
    @ResponseBody
    fun sendFormForID(@PathVariable(value = "form_id") form_id: String): ResponseEntity<Any> {
        /* Check if Int / Valid form ID */
        if(!form_id.isInt()) { return ResponseEntity(returnError("Invalid user id, check syntax"), HttpStatus.OK) }

        /* Last Check Point if this Passes we can now assign values and get the rest of the data
        *  Checking if form_info_id exist.*/
        if(!formRepo.existFormById(form_id.toInt())) {
            return ResponseEntity(returnError("No form with this ID was found"), HttpStatus.OK)
        }

        /* Getting Form Info */
        val formInfo: FormInfo = formInfoHead.getFormInfoByFormId(form_id.toInt())

        /* Creating Info JSON Object*/
        val returnInfo = JsonObject()
        returnInfo.addProperty("title", formInfo.title)
        returnInfo.addProperty("subtitle", formInfo.subtitle)

        /* Getting Info Items - for then to map it and add it into info JSON Object */
        val allInfoById = formInfoItems.getAllItemsById(formInfo.form_info_id)

        /* Mapping Items */
        val mappedInfoItems = allInfoById.map { item: FormInfoItems -> item.getItemInJSON()}

        /* Adding infoItems to main Info */
        returnInfo.addProperty("info", mappedInfoItems.toString())

        /* Getting All Form Items - Then Mapping and adding */
        val allFormItems = formItemRepo.getAllItemsById(form_id.toInt())

        /* Mapping Items */
        val mappedFormItem = allFormItems.map { item: FormItems -> item.getItemInJSON() }

        /* Creating Main Return JSON */
        val returnObject = JsonObject()
        returnObject.addProperty("formInfo", returnInfo.toString().removeBackSlash())
        returnObject.addProperty("formItems", mappedFormItem.toString().removeBackSlash())

        /* Returning JSON Object. */
        return ResponseEntity(returnObject.toString().removeBackSlash().removeExtra(), HttpStatus.OK)
    }
}

/* Data Classes for intern use */
data class Message(val status: String?, val message: String)
data class NewUser(val username:String, val password: String, val email: String)
data class NewFormData(val formTime: String, val formItems: List<FormItems>, val formInfo: InfoData)
data class InfoData(val title: String, val subtitle: String, val info: List<FormInfoItems>)

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

fun String.removeBackSlash(): String {
    return this.replace("\\\"", "\"")
}

fun String.removeQuote(): String {
    return this.replace("\"", "")
}

fun String.removeExtra(): String {
   return this.replace(":\"{", ":{").replace("}\",", "},").replace(":\"[", ":[").replace("]\",", "]").replace("]\"}", "]}")
}

/* Return New User Data */
fun returnNewUser(userid: String?, userdata: NewUser): ResponseEntity<Any> {
    val returnObject = JsonObject()
    returnObject.addProperty("userid", userid)
    returnObject.addProperty("username", userdata.username)
    returnObject.addProperty("email", userdata.email)

    return ResponseEntity(returnObject.toString(), HttpStatus.OK)
}

