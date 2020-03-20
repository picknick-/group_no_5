package org.hack.example.group_no_5.entities

import androidx.room.*

@Entity
data class Answer(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "answerText") val answerText: String?,
    val questionId: Long
)

data class AnswerWithNextQuestion(
    @Embedded val answer: Answer,
    @Relation(
        parentColumn = "uid",
        entityColumn = "previousQuestion"
    )
    val nextQuestion: Question
)