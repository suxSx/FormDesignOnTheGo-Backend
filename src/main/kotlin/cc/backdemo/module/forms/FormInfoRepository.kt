package cc.backdemo.module.forms

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/* Repo for formInfo Head Table */
@Repository
interface FormInfoRepository : JpaRepository<FormInfo, Long> {
    /* Query for getting  */
    @Query(value = "SELECT * FROM form_info WHERE form_id = ?1", nativeQuery = true)
    fun getFormInfoByFormId(form_id: Int): FormInfo
}