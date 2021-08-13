package cc.backdemo.module.account

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.persistence.Tuple

/* We need here in JPA Repo to pass the type we want to work with, in this example will we pass the
* Accounts as type and the id. In this case it will be LONG as it is the Accounts ID type set with the @ID
* This Interface will conect to the Class we have created that will connect to our database.
* Inside JpdaRepo. It links to CrudRepo who have the basic commands such as:
* - findAll()
* - deleteAll()
* - count()
* - findAllByID
*
* If this is all we need, we dont need to do anything more than decalring it like this. And
* we can then in our Application file connect to it. */
@Repository
public interface AccountsRepository:  JpaRepository<Accounts, Long> {
    /* Query for getting userid based on username */
    @Query(value = "SELECT user_id FROM Accounts WHERE username = ?1", nativeQuery = true)
    fun findUserIdByUsername(username: String): String?

    /* Query for getting userid based on email */
    @Query(value = "SELECT user_id from Accounts WHERE email = ?1", nativeQuery = true)
    fun findUserIdByEmail(email: String): String?

    /* Query Single User Info with User_id, returning tuple list, in order to also get the table names */
    @Query(value = "SELECT user_id, username, email, created_on from Accounts WHERE user_id = ?1", nativeQuery = true)
    fun findBasicUserDataByID(user_id: Int): List<Tuple>
}