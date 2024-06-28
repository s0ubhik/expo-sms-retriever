package expo.modules.smsretriever

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class AppSignatureHelper (private val context: Context){

    companion object {
        private const val TAG = "AppSignatureHelper"
        private const val HASH_TYPE = "SHA-256"
        private const val NUM_HASHED_BYTES = 9
        private const val NUM_BASE64_CHAR = 11

        private fun hash(packageName: String, signature: String): String? {
            val appInfo = "$packageName $signature"
            return try {
                val messageDigest = MessageDigest.getInstance(HASH_TYPE)
                val hashSignature = messageDigest.digest(appInfo.toByteArray(StandardCharsets.UTF_8))
                    .copyOfRange(0, NUM_HASHED_BYTES)
                val base64Hash = Base64.encodeToString(hashSignature, Base64.NO_PADDING or Base64.NO_WRAP)
                    .substring(0, NUM_BASE64_CHAR)
                Log.d(TAG, "pkg: $packageName -- hash: $base64Hash")
                base64Hash
            } catch (e: NoSuchAlgorithmException) {
                Log.e(TAG, "hash:NoSuchAlgorithm", e)
                null
            }
        }
    }

    fun getAppSignatures(): List<String> {
        val appCodes = mutableListOf<String>()
        val packageName = context.packageName
        val packageManager = context.packageManager
        try {
            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES).signingInfo.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures
            }
            signatures.mapNotNullTo(appCodes) { hash(packageName, it.toCharsString()) }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "Unable to find package to obtain hash.", e)
        }
        return appCodes
    }
}