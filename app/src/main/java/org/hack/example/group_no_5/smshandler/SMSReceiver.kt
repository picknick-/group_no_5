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
                val question = getCurrentQuestion(message, it)
                val nextQuestion = question?.let { it1 -> getNextQuestion(message, it1, it) }
                val result = nextQuestion?.let { it1 -> formatQuestion(it1) }
                sendSMS(message.originatingAddress, result)
            }
        }
    }

    private fun getNextQuestion(
        message: SmsMessage,
        question: Question,
        context: Context
    ): QuestionWithAnswers {
        val response = message.messageBody
        val questionDatabase = QuestionDatabase.getDatabase(context)
        val questionWithAnswers = questionDatabase.questionDao().loadByIds(question.Qid)
        questionWithAnswers.answers.forEach { answer ->
            if (answer.answerText.equals(response,true)) {
                val nextQuestion = questionDatabase.questionDao().loadByIds(answer.nextQuestion)
                if (nextQuestion.answers.isEmpty()){
                    questionDatabase.run { userDao().delete(userDao().findByNumber(message.originatingAddress)!!.user) }
                }
                return nextQuestion
            }
        }
        return questionWithAnswers
    }

    private fun getCurrentQuestion(message: SmsMessage, context: Context): Question? {
        val phoneNumber = message.originatingAddress
        val questionDatabase = QuestionDatabase.getDatabase(context)
        var user = questionDatabase.userDao().findByNumber(phoneNumber) ?: createNewUser(
            phoneNumber,
            context
        )
        return user?.currentQuestion

    }


    private fun formatQuestion(question: QuestionWithAnswers): String {
        val result = StringJoiner(" | ")
        result.add(question.question.Question)
        question.answers.forEach {
            result.add(it.answerText)
        }
        return result.toString()
    }

//Sends an SMS message to another device

    //Sends an SMS message to another device
    private fun sendSMS(phoneNumber: String?, message: String?) {
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, null, null)
    }

    private fun createNewUser(number: String, context: Context): UserWithCurrentQuestion? {
        val questionDatabase = QuestionDatabase.getDatabase(context)
        val id = questionDatabase.questionDao().getDefault().question.Qid
        var user = User(number, id)
        questionDatabase.userDao().insert(user)
        return questionDatabase.userDao().findByNumber(number)
    }
}
