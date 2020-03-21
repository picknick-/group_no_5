package org.hack.example.group_no_5.smshandler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.SmsMessage
import org.hack.example.group_no_5.databases.QuestionDatabase
import org.hack.example.group_no_5.entities.Question
import org.hack.example.group_no_5.entities.QuestionWithAnswers
import org.hack.example.group_no_5.entities.User
import org.hack.example.group_no_5.entities.UserWithCurrentQuestion
import java.util.*

const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        context?.let {
            val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            smsMessages.forEach { message ->
                val user = getCurrentUser(message, it)
                val question = user.currentQuestion
                val nextQuestion = getNextQuestion(message, question, user.user, it)
                user.currentQuestion = nextQuestion.question

                val result = nextQuestion?.let { it1 -> formatQuestion(it1) }
                sendSMS(message.originatingAddress, result)
            }
        }
    }

    private fun getCurrentUser(message: SmsMessage?, context: Context): UserWithCurrentQuestion {
        val phoneNumber = message!!.originatingAddress
        val questionDatabase = QuestionDatabase.getDatabase(context)
        var user = questionDatabase.userDao().findByNumber(phoneNumber) ?: createNewUser(
            phoneNumber,
            context
        )
        return user!!
    }

    private fun getNextQuestion(
        message: SmsMessage,
        question: Question,
        user: User,
        context: Context
    ): QuestionWithAnswers {
        val response = message.messageBody
        val questionDatabase = QuestionDatabase.getDatabase(context)
        val questionWithAnswers = questionDatabase.questionDao().loadByIds(question.qid)
        questionWithAnswers.answers.forEach { answer ->
            if (answer.answerText.equals(response, true)) {
                if (answer.nextQuestion != 0L) {
                    val nextQuestion = questionDatabase.questionDao().loadByIds(answer.nextQuestion)
                    user.currentQuestionID = nextQuestion.question.qid
                    if (nextQuestion.answers.isEmpty()){
                    questionDatabase.run { userDao().delete(user) }

                    }else {
                        questionDatabase.run { userDao().update(user) }
                    }
                    return nextQuestion

                }
            }
        }

        return questionWithAnswers
    }


    private fun formatQuestion(question: QuestionWithAnswers): String {
        val result = StringJoiner(" | ")
        result.add(question.question.Question)
        question.answers.forEach {
            result.add(it.answerText)
        }
        return result.toString()
    }

    private fun sendSMS(phoneNumber: String?, message: String?) {
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, null, null)
    }

    private fun createNewUser(number: String, context: Context): UserWithCurrentQuestion? {
        val questionDatabase = QuestionDatabase.getDatabase(context)
        val id = questionDatabase.questionDao().getDefault().question.qid
        var user = User(number, id)
        questionDatabase.userDao().insert(user)
        return questionDatabase.userDao().findByNumber(number)
    }
}
