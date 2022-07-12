package com.example.jayesh.BroadcastReciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast


class IncominSmsReciever : BroadcastReceiver() {
    val sms = SmsManager.getDefault()
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObj = bundle["pdus"] as Array<*>?
                for (i in pdusObj!!.indices) {
                    val currentMessage = SmsMessage.createFromPdu(
                        pdusObj[i] as ByteArray
                    )
                    val phoneNumber = currentMessage.displayOriginatingAddress
                    val senderNum = phoneNumber
                    val message = currentMessage.displayMessageBody
                    if (message.contains("JK")) {
                        Toast.makeText(context, "Recived msg", Toast.LENGTH_SHORT).show()
                        val gotomain = Intent()
                        gotomain.putExtra("msg", message)
                        gotomain.setClassName(
                            "com.example.jayesh",
                            "com.example.jayesh.Activities.EmergencyActivity"
                        )
                        gotomain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(gotomain)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "onReceive: $e")
        }
    }

    companion object {
        const val TAG = "BroadcastReciverError"
    }
}
