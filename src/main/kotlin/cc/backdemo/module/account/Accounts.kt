package cc.backdemo.module.account

/* Using Entity here to map a class to a table in postgresql */

/* This libares are imported from the implementation("org.springframework.boot:spring-boot-starter-data-jpa")
* found in gradle build */
import javax.persistence.*

/* Creating a Entity that map its self to a table with the same name as the class. If the class name is not the same as
* the table you need to set a name. We do this in the @Table label. But anyway it is good practice to always name both. */
@Entity
@Table(
    name = "accounts",
    schema = "public"
)
class Accounts(
    /* Mapping ID to be the primary key, with Entity, this is done by using the label  @ID. Before the class we also
    * say that the class itself is a entity. So on compile it will try to find a table that has the same name as the
    * class and map it. Also we can use name here to name a reference to the table if we want different names in our
    * class script. You can also set propterties for the rows under  @collum as f.eks if it should be
    * null, text, type, int and all you would like. This is not necessary here since we use flyway to create and
    * manage our database. */
    @Column( name = "username" )
    private var username: String,

    @Column( name = "password" )
    private var password: String,

    @Column( name = "email" )
    private var email: String,

    @Column( name = "last_login" )
    private var lastLogin: String,

    @Column( name = "created_on" )
    private var createdOn: String,

    @Id
    @SequenceGenerator(name = "accounts_user_id_seq", sequenceName = "accounts_user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_user_id_seq")
    @Column( name = "user_id", columnDefinition = "serial")
    private var userID: Int?=null
) {
    constructor() : this("", "", "", "", "")

    override fun toString(): String {
        return "Accounts(userID=$userID, username='$username', password='$password', email='$email', lastLogin='$lastLogin', createdOn='$createdOn')"
    }

    fun getBasic(): BasicData {
        return BasicData(username, email, lastLogin, createdOn)
    }
}

data class BasicData(val username: String, val email: String, val lastLogin: String, val createdOn: String)