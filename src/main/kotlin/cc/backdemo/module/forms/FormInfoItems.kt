package cc.backdemo.module.forms

/* Using Entity here to map a class to a table in postgresql */

/* This libares are imported from the implementation("org.springframework.boot:spring-boot-starter-data-jpa")
* found in gradle build */
import com.google.gson.JsonObject
import javax.persistence.*

@Entity
@Table (
    name = "info",
    schema = "public"
)
class FormInfoItems (
    /* ID placed last for easy adding of data, only one imput are required and it is time of creation */
    @Column (name = "form_info_id")
    public var form_info_id: Int?=null,

    /* Form Action String */
    @Column (name = "action")
    public var action: String,

    /* Form Action Icon */
    @Column (name = "icon")
    public var icon: String,

    /* Form Action id */
    @Column (name = "id")
    public var id: Int,

    /* Form Action text */
    @Column (name = "text")
    public var text: String,

    /* Form Action type */
    @Column (name = "type")
    public var type: String,

    @Id
    @SequenceGenerator(name = "info_info_id_seq", sequenceName = "info_info_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "info_info_id_seq")
    @Column( name = "info_id", columnDefinition = "serial")
    public var info_id: Int?=null,

) {
    constructor() : this(0, "", "", 0, "", "") { }

    fun getItemInJSON(): String {
        val infoItem = JsonObject()
        infoItem.addProperty("action", action)
        infoItem.addProperty("icon", icon)
        infoItem.addProperty("id", id)
        infoItem.addProperty("text", text)
        infoItem.addProperty("type", type)
        return infoItem.toString().replace("\\", "")
    }
}