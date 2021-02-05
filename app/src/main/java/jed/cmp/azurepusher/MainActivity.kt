package jed.cmp.azurepusher

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.RemoteMessage
import com.pusher.pushnotifications.PushNotificationReceivedListener
import com.pusher.pushnotifications.PushNotifications
import com.squareup.picasso.Picasso
import jed.cmp.azurepusher.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        PushNotifications.start(applicationContext, "04374500-fb5e-41b6-9bbe-9acb3ee2e174")
        PushNotifications.addDeviceInterest("debug-hello")
        PushNotifications.addDeviceInterest("ethereum-gas-prices")
        PushNotifications.addDeviceInterest("alpaca-trading-bot")
        setContentView(binding.root)
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val preferenceImageUrl = sharedPref.getString(
            getString(R.string.preference_image_url_key),
            getString(R.string.default_image_url)
        )
        val notificationImageUrl =
            if (intent.extras != null) intent.extras!!.getString(getString(R.string.notification_image_url_key)) else null
        val imageUrl =
            if (!isNullOrEmpty(notificationImageUrl)) notificationImageUrl else preferenceImageUrl
        savePrefAndLoadImage(imageUrl!!)
    }

    override fun onResume() {
        super.onResume()
        PushNotifications.setOnMessageReceivedListenerForVisibleActivity(
            this,
            object : PushNotificationReceivedListener {
                override fun onMessageReceived(remoteMessage: RemoteMessage) {
                    val imageUrl =
                        remoteMessage.data[getString(R.string.notification_image_url_key)]
                    if (!isNullOrEmpty(imageUrl)) {
                        val uiHandler = Handler(Looper.getMainLooper())
                        uiHandler.post {
                            savePrefAndLoadImage(imageUrl!!)
                        }
                    }
                }
            })
    }

    fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && str.trim().isNotEmpty())
            return false
        return true
    }

    fun savePrefAndLoadImage(imageUrl: String) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.preference_image_url_key), imageUrl)
            apply()
        }
        Picasso
            .get()
            .load(imageUrl)
            .into(binding.imageView)
    }
}