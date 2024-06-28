package expo.modules.smsretriever

import android.content.Context
import expo.modules.kotlin.Promise
import expo.modules.kotlin.exception.Exceptions
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class SmsRetrieverModule : Module() {
  private val context: Context
    get() = appContext.reactContext ?: throw Exceptions.ReactContextLost()

  override fun definition() = ModuleDefinition {

    val signHelper = AppSignatureHelper(context);
    val smsHelper = SmsRetrieverHelper(context);

    Name("SmsRetriever")

    Events("onMessage")

    AsyncFunction("getHash") { promise: Promise ->
      try {
        val signatures = signHelper.getAppSignatures()
        promise.resolve(signatures)
      } catch (e: Exception) {
        promise.resolve(mutableListOf(String));
      }
    }

    AsyncFunction("start") { promise: Promise ->
      try {
        /* register listener */
        smsHelper.register(object : SMSListener{
          override fun onReceive(message: String) {
            sendEvent("onMessage", mapOf(
              "value" to message,
              "otp" to smsHelper.getOTP(message)
            ))
          }
        });

        /* start listener */
        smsHelper.start();
        promise.resolve(true);

      } catch (e: Exception) {
        promise.resolve(false);
      }
    }

    AsyncFunction("stop") { promise: Promise ->
      /* unregister listener */
      try {
        smsHelper.unregister();
        promise.resolve(true);
      } catch (e: Exception) {
        promise.resolve(false);
      }
    }

  }
}
