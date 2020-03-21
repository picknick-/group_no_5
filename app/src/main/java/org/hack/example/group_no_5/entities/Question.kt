package org.hack.example.group_no_5.entities

import androidx.room.*


@Entity
data class Question(
    @ColumnInfo(name = "question") val Question: String?
) {
    @PrimaryKey(autoGenerate = true)
    var qid: Long = 0
}


data class QuestionWithAnswers(
    @Embedded val question: Question,
    @Relation(parentColumn = "qid", entityColumn = "questionId")
    val answers: List<Answer>)

data class NewQuestion(
    val question: Question,
    val answers: List<NewAnswer>?
)