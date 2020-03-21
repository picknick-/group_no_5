package org.hack.example.group_no_5.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.room.*
import org.hack.example.group_no_5.entities.*


@Dao
abstract class QuestionDAO {

    open fun insertAll(newQuestion: NewQuestion): Long {
        if (newQuestion.question.qid == 0L) {
            newQuestion.question.qid = insert(newQuestion.question)
            newQuestion.answers?.forEach { answer ->
                answer.answer.questionId = newQuestion.question.qid
                answer.answer.nextQuestion = insertAll(answer.nextQuestion)
                insertAll(answer.answer)
            }
        }
        return newQuestion.question.qid
    }


    @Transaction
    @Query("SELECT * FROM question")
    abstract fun getAll(): List<QuestionWithAnswers>


    @Query("SELECT * FROM question WHERE Qid ==:qId")
    @Transaction
    abstract fun loadByIds(qId: Long): QuestionWithAnswers

    @Insert
    abstract fun insertAll(vararg questions: Question)

    @Insert
    abstract fun insert(question: Question): Long

    @Insert
    abstract fun insertAll(vararg answer: Answer)

    @Insert
    abstract fun insertAll(answer: List<Answer>)

    @Delete
    abstract fun delete(answer: Answer)

    @Update
    abstract fun update(question: Question)
}
