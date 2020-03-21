package org.hack.example.group_no_5

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.hack.example.group_no_5.databases.QuestionDatabase
import org.hack.example.group_no_5.entities.*


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



        prepareQuestions()

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
        qDatabase.clearAllTables()

        var answersDefaultQuestion = ArrayList<NewAnswer>()
        var defaultQuestion = NewQuestion(Question("To start test type help"), answersDefaultQuestion)
        var answersTemperature = ArrayList<NewAnswer>()
        answersDefaultQuestion.add(
            NewAnswer(
                Answer("help"),
                NewQuestion(Question("You are sick"), answersTemperature)
            )
        )


//        var yesQuestion = Question("You are sick")
//        var noQuestion = Question("You are not sick")
//        qDatabase.questionDao().insertAll(yesQuestion, noQuestion)
//        var defaultQuestion = QuestionWithAnswers(
//            Question("Are you sick?"),
//            listOf(answerNo.answer, answerYes.answer)
//        )
        defaultQuestion.question.qid = 1
        qDatabase.questionDao().insertAll(defaultQuestion)
    }

}


