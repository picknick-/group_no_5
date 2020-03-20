package org.hack.example.group_no_5.entities

import androidx.room.*


@Entity
data class Question(
    @PrimaryKey(autoGenerate = true) val Qid: Long,
    @ColumnInfo(name = "question") val Question: String?,
    val previousQuestion: Long? = null
)


data class QuestionWithAnswers(
    @Embedded val question: Question,
    @Relation(parentColumn = "Qid", entityColumn = "questionId")
    val answers: List<Answer>
)