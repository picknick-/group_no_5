package org.hack.example.group_no_5.dao

import androidx.room.*
import org.hack.example.group_no_5.entities.*

@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM user")
    fun getAll(): List<UserWithCurrentQuestion>

    @Transaction
    @Query(
        "SELECT * FROM user WHERE telephoneNumber LIKE :telephoneNumber LIMIT 1"
    )
    fun findByNumber(telephoneNumber: String): UserWithCurrentQuestion?

    @Insert
    fun insert(vararg user: User)

    @Delete
    fun delete(user: User)
}
