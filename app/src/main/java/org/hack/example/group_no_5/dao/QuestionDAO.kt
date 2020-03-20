package org.hack.example.group_no_5.dao

import androidx.room.*
import org.hack.example.group_no_5.entities.Answer
import org.hack.example.group_no_5.entities.Question
import org.hack.example.group_no_5.entities.QuestionWithAnswers
import org.hack.example.group_no_5.entities.User

@Dao
interface QuestionDAO {
    @Transaction
    @Query("SELECT * FROM question")
    fun getAll(): List<QuestionWithAnswers>

    @Query("SELECT * FROM question where Qid==1")
    @Transaction
    fun getDefault(): QuestionWithAnswers


    @Query("SELECT * FROM question WHERE Qid ==:qId")
    @Transaction
    fun loadByIds(qId: Long): QuestionWithAnswers

//    @Query(
//        "SELECT * FROM user WHERE first_name LIKE :first AND " +
//                "last_name LIKE :last LIMIT 1"
//    )
//    fun findByName(first: String, last: String): Answer

    @Insert
    fun insertAll(vararg question: Question)

    @Insert
    fun insertAll(vararg answer: Answer)

    @Insert
    fun insert(vararg user: User)

    @Delete
    fun delete(answer: Answer)
}
