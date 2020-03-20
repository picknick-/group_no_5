package org.hack.example.group_no_5

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.hack.example.group_no_5.databases.QuestionDatabase
import org.hack.example.group_no_5.entities.Answer
import org.hack.example.group_no_5.entities.Question


const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS
            ),
            MY_PERMISSIONS_REQUEST_READ_CONTACTS
        )


        var default =
            QuestionDatabase.getDatabase(this.applicationContext).questionDao().getAll()
        if (default.isEmpty()) {
            prepareQuestions()
        }

        setContentView(R.layout.activity_main)
    }


    fun acceptAnswer(view: View) {
        val intent = Intent(this, Result::class.java).apply {
            putExtra(EXTRA_MESSAGE, getString(R.string.Positive))
        }
        startActivity(intent)
    }

    fun declineAnswer(view: View) {
        val intent = Intent(this, Result::class.java).apply {
            putExtra(EXTRA_MESSAGE, getString(R.string.Negative))
        }
        startActivity(intent)
    }

    private fun prepareQuestions() {
        var qDatabase = QuestionDatabase.getDatabase(this.applicationContext)
        var defaultQuestion = Question(1, "Are you sick?")
        var yesQuestion = Question(2, "You are sick")
        var noQuestion = Question(3, "You are not sick")
        var answerYes = Answer(1, "Yes", defaultQuestion.Qid, yesQuestion.Qid)
        var answerNo = Answer(2, "No", defaultQuestion.Qid, noQuestion.Qid)
//        var qwa1 = QuestionWithAnswers(defaultQuestion, listOf(answerYes, answerNo))
//        qDatabase.questionDao().insertAll(qwa1)
        qDatabase.questionDao().insertAll(defaultQuestion, yesQuestion, noQuestion)
        qDatabase.questionDao().insertAll(answerYes, answerNo)
    }

}


