package org.hack.example.group_no_5.dao

import androidx.room.*
import org.hack.example.group_no_5.entities.*

@Dao
abstract class UserDao {
    @Transaction
    @Query("SELECT * FROM user")
    abstract fun getAll(): List<UserWithCurrentQuestion>

    @Transaction
    @Query(
        "SELECT * FROM user WHERE telephoneNumber LIKE :telephoneNumber LIMIT 1"
    )
    abstract fun findByNumber(telephoneNumber: String): UserWithCurrentQuestion?

    @Insert
    abstract fun insert(user: User)

    @Delete
    abstract fun delete(user: User)

    @Update
    abstract fun update(user: User)

}
