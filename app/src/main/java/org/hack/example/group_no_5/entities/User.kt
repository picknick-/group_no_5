package org.hack.example.group_no_5.entities

import androidx.room.*

@Entity
data class User(
    @PrimaryKey val telephoneNumber: String,
    @ColumnInfo(name = "currentQuestionID") var currentQuestionID: Long
)

data class UserWithCurrentQuestion(
    @Embedded val user: User,
    @Relation(parentColumn = "currentQuestionID", entityColumn = "Qid")
    val currentQuestion: Question
)