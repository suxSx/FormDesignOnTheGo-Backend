package cc.backdemo.module.forms

/* This libares are imported from the implementation("org.springframework.boot:spring-boot-starter-data-jpa")
* found in gradle build */
import cc.backdemo.removeBackSlash
import com.google.gson.JsonObject
import javax.persistence.*

@Entity
@Table (
    name = "form_items",
    schema = "public"
)
class FormItems (
    /* PK ID placed last for easy constrcutor without ID */
    @Column(name = "form_id")
    public var form_id: Int?=null,

    /* Form description String */
    @Column (name = "description")
    public var description: String = "",

    /* Form error String */
    @Column (name = "error")
    public var error: String = "",

    /* Form id int */
    @Column (name = "id")
    public var id: Int,

    /* Form needed String */
    @Column (name = "needed")
    public var needed: Int = 0,

    /* Form options String */
    @Column (name = "options")
    public var options: String = "",

    /* Form rank String */
    @Column (name = "rank")
    public var rank: Int,

    /* Form title String */
    @Column (name = "title")
    public var title: String = "",

    /* Form type String */
    @Column (name = "type")
    public var type: String = "",

    /* Form validation String */
    @Column (name = "validation")
    public var validation: String = "",

    @Id
    @SequenceGenerator(name = "form_items_form_items_id_seq", sequenceName = "form_items_form_items_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "form_items_form_items_id_seq")
    @Column( name = "form_items_id", columnDefinition = "serial")
    public var form_items_id: Int?=null,
) {
    constructor() : this(0, "", "", 0, 0, "", 0,"","","") {}

    fun getItemInJSON(): String {
        val infoItem = JsonObject()
        infoItem.addProperty("id", id)
        infoItem.addProperty("type", type)
        infoItem.addProperty("title", title)
        infoItem.addProperty("description", description)
        infoItem.addProperty("needed", needed)
        infoItem.addProperty("options", options)
        infoItem.addProperty("rank", rank)
        infoItem.addProperty("validation", validation)
        infoItem.addProperty("error", error)
        return infoItem.toString().removeBackSlash()
    }
}