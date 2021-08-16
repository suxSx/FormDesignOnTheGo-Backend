package cc.backdemo.module.forms

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FormInfoItemsRepository: JpaRepository<FormInfoItems, Long> {
    @Query(value = "SELECT * FROM info WHERE form_info_id = ?1", nativeQuery = true)
    fun getAllItemsById(form_info_id: Int?): List<FormInfoItems>
}