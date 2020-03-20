package org.hack.example.group_no_5.smshandler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsManager
import org.hack.example.group_no_5.databases.QuestionDatabase
import org.hack.example.group_no_5.entities.QuestionWithAnswers
import java.util.*

const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        smsMessages.forEach {
            val message: String = it.displayMessageBody
            val result = getResult(message, context)
            if (result != null) {
                sendSMS(it.originatingAddress, result)
            }
        }
    }

    private fun getResult(message: String, context: Context?): String? {
        val questionDatabase = context?.let { QuestionDatabase.getDatabase(it) }
        return questionDatabase?.questionDao()?.getDefault()?.let { formatQuestion(it) }
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
    private fun sendSMS(phoneNumber: String, message: String) {
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, null, null)
    }
}
