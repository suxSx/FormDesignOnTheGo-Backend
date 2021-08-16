package cc.backdemo.module.forms

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FormItemsRepository: JpaRepository<FormItems, Long> {
    @Query(value = "SELECT * FROM form_items WHERE form_id = ?1", nativeQuery = true)
    fun getAllItemsById(form_id: Int?): List<FormItems>
}