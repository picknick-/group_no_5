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
var defaultQuestion : Question = Question("To start test type help")

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
        var defaultNewQuestion = NewQuestion(defaultQuestion,answersDefaultQuestion)

        var answersTemperature = ArrayList<NewAnswer>()
        answersDefaultQuestion.add(
            NewAnswer(
                Answer("help"),
                NewQuestion(
                    Question("Do you have temperature over or under 38 C?"),
                    answersTemperature
                )
            )
        )
        var answersContact = ArrayList<NewAnswer>()
        var questionContact =
            NewQuestion(
                Question("During last 14 days did you have a contact with a person with confirmed or possible SARS-CoV-2 infection?"),
                answersContact
            )
        var answersSymptoms = ArrayList<NewAnswer>()

        var questionSymptoms =
            NewQuestion(
                Question("Do you currently suffer from cough or dyspnoea(difficulty breathing)?"),
                answersSymptoms
            )

        answersTemperature.add(
            NewAnswer(
                Answer("over"),
                questionContact
            )
        )

        answersTemperature.add(
            NewAnswer(
                Answer("below"),
                questionSymptoms
            )
        )

        var answersOtherSymptoms = ArrayList<NewAnswer>()

        var questionOtherSymptoms =
            NewQuestion(
                Question("Do you currently have other symptoms of infection (sore throat, runny nose)?"),
                answersOtherSymptoms
            )

        var resultStayHome =
            NewQuestion(Question("Stay Home for 14 days. Thank you for cooperation."), null)

        answersContact.add(
            NewAnswer(
                Answer(
                    "Yes"
                ), resultStayHome
            )
        )

        answersContact.add(
            NewAnswer(
                Answer(
                    "No"
                ), questionOtherSymptoms
            )
        )



        var resultContactDoctor =
            NewQuestion(
                Question("Avoid contact with other people, preferable wear a face mask. Immediately call the closest hospital infectious hospital or Sanepid. Thank you for cooperation"),
                null
            )


        answersSymptoms.add(
            NewAnswer(
                Answer(
                    "Yes"
                ), resultContactDoctor
            )
        )

        answersSymptoms.add(
            NewAnswer(
                Answer(
                    "No"
                ), questionOtherSymptoms
            )
        )

        var resultNegative =
            NewQuestion(
                Question("Stay at home and limit your time outside, contact with other people, (only essential shopping, short walks) but even then avoid socializing with people. Thank you for cooperation"),
                null
            )

        answersOtherSymptoms.add(
            NewAnswer(
                Answer(
                    "Yes"
                )
                , resultStayHome
            )
        )

        answersOtherSymptoms.add(
            NewAnswer(
                Answer(
                    "No"
                )
                , resultNegative
            )
        )

        qDatabase.run { questionDao().insertAll(defaultNewQuestion) }
    }
}


