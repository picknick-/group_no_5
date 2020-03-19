package org.hack.example.group_no_5.smshandler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.provider.Telephony
import android.telephony.SmsManager
import org.hack.example.group_no_5.R

const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        smsMessages.forEach {
            val phoneNumber: String = it.displayOriginatingAddress

            val message: String = it.displayMessageBody
            val result = getResult(message, context)
            if (result != null) {
                sendSMS(it.originatingAddress, result)
            }
        }
    }

    private fun getResult(message: String, context: Context?): String? {
        if (message.equals("Yes")) {
            return context?.getString(R.string.Positive)
        }
        if (message.equals("No")) {
            return context?.getString(R.string.Negative)
        }
        return "Please try again"
    }


//Sends an SMS message to another device

    //Sends an SMS message to another device
    private fun sendSMS(phoneNumber: String, message: String) {
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, null, null)
    }
}
