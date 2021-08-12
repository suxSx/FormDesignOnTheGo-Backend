package cc.backdemo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

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

}