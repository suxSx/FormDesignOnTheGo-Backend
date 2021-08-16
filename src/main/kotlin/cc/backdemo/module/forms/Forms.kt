package cc.backdemo.module.forms

/* Using Entity here to map a class to a table in postgresql */

/* This libares are imported from the implementation("org.springframework.boot:spring-boot-starter-data-jpa")
* found in gradle build */
import com.google.gson.JsonObject
import javax.persistence.*

/* Entity used to map main form database */
@Entity
@Table(
    name = "forms",
    schema = "public"
)
class Forms (
    /* ID placed last for easy adding of data, only one imput are required and it is time of creation */
    @Column (name = "form_time")
    public var form_time: String,

    @Id
    @SequenceGenerator(name = "forms_form_id_seq", sequenceName = "forms_form_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forms_form_id_seq")
    @Column( name = "form_id", columnDefinition = "serial")
    public var form_id: Int?=null,
) {
    constructor() : this ( "" )
}