package org.hack.example.group_no_5.entities

import androidx.room.*

@Entity
data class Answer(
    @ColumnInfo(name = "answerText") val answerText: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
    var questionId: Long = 0
    var nextQuestion: Long = 0
}

data class AnswerWithNextQuestion(
    @Embedded val answer: Answer,
    @Relation(
        parentColumn = "nextQuestion",
        entityColumn = "qid"
    )
    val nextQuestion: Question
)

data class NewAnswer(
    val answer: Answer,
    val nextQuestion: NewQuestion
)