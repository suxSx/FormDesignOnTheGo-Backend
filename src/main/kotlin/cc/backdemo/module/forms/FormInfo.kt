package cc.backdemo.module.forms

/* Using Entity here to map a class to a table in postgresql */

/* This libares are imported from the implementation("org.springframework.boot:spring-boot-starter-data-jpa")
* found in gradle build */
import com.google.gson.JsonObject
import javax.persistence.*

@Entity
@Table (
    name = "form_info",
    schema = "public"
)
class FormInfo (
    /* ID placed last for easy adding of data, only one imput are required and it is time of creation */
    @Column (name = "form_id")
    public var form_id: Int?=null,

    /* Form Action String */
    @Column (name = "title")
    public var title: String,

    /* Form Action Icon */
    @Column (name = "subtitle")
    public var subtitle: String,

    @Id
    @SequenceGenerator(name = "form_info_form_info_id_seq", sequenceName = "form_info_form_info_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "form_info_form_info_id_seq")
    @Column( name = "form_info_id", columnDefinition = "serial")
    public var form_info_id: Int?=null,
) {
    constructor() : this(0, "", "") {
    }
}