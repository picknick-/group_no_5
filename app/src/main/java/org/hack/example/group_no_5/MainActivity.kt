package org.hack.example.group_no_5

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.hack.example.group_no_5.smshandler.SmsReceiver


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

}


