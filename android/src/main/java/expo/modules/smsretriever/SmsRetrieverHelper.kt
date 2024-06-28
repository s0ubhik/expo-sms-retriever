package expo.modules.smsretriever

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.BroadcastReceiver
import android.os.Build
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

import android.util.Log

interface SMSListener {
    fun onReceive(message: String)
}

@Suppress("DEPRECATION")
class SMSReceiver : BroadcastReceiver() {
    private var listener: SMSListener? = null

    fun setSMSListener(listener: SMSListener?) {
        this.listener = listener
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            val extras = intent.extras
            val status = extras!![SmsRetriever.EXTRA_STATUS] as Status?
            when (status!!.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val sms = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String
                    if (listener != null) {
                        listener!!.onReceive(sms)
                    }
                }
            }
        }
    }
}

class SmsRetrieverHelper(private val context: Context) {
    private val TAG = "retrieverHelper"
    private var isRegistered : Boolean = false
    private var smsReceiver: SMSReceiver = SMSReceiver()
    private val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)

    fun register(listener: SMSListener)
    {
        smsReceiver.setSMSListener(listener)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(smsReceiver, intentFilter,
            SmsRetriever.SEND_PERMISSION,
            null, Context.RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(smsReceiver, intentFilter)
        }
        Log.d(TAG, "listener registered")
        isRegistered = true
    }

    fun unregister()
    {
        context.unregisterReceiver(smsReceiver)
        isRegistered = false
        Log.d(TAG, "listener un-registered")
    }

    fun start()
    {
        val client = SmsRetriever.getClient(context)
        client.startSmsRetriever()
        Log.d(TAG, "listener started")
    }

    fun getOTP(message: String): String? {
        val otpPattern = "\\b\\d+\\b".toRegex()
        val otp = otpPattern.find(message)
        return otp?.value
    }
}