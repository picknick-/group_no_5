package org.hack.example.group_no_5.entities

import androidx.room.*

@Entity
data class Answer(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "answerText") val answerText: String?,
    val questionId: Long,
    val nextQuestion: Long
)

data class AnswerWithNextQuestion(
    @Embedded val answer: Answer,
    @Relation(
        parentColumn = "nextQuestion",
        entityColumn = "Qid"
    )
    val nextQuestion: Question
)