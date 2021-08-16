package cc.backdemo.module.forms

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.persistence.Tuple

/* Repo for the forms main table */

@Repository
interface FormsRepository: JpaRepository<Forms, Long> {
    /* Query for checking if Form Exist */
    @Query(value = "SELECT exists (SELECT 1 FROM forms WHERE form_id = ?1 LIMIT 1)", nativeQuery = true)
    fun existFormById(form_id: Int): Boolean

    @Query(value = "SELECT form_id FROM forms WHERE form_time = ?1", nativeQuery = true)
    fun getFormIdByTime(form_time: String): String
}