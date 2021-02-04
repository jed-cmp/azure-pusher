package jed.cmp.azurepusher

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
    private var imageUrlKey = "image-url"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        PushNotifications.start(applicationContext, "04374500-fb5e-41b6-9bbe-9acb3ee2e174")
        PushNotifications.addDeviceInterest("debug-hello")
        PushNotifications.addDeviceInterest("ethereum-gas-prices")
        PushNotifications.addDeviceInterest("alpaca-trading-bot")
        setContentView(binding.root)
        if (intent.extras != null) {
            val imageUrl = intent.extras!!.getString(imageUrlKey)
            if (!isNullOrEmpty(imageUrl)) {
                Picasso
                    .get()
                    .load(imageUrl)
                    .into(binding.imageView)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        PushNotifications.setOnMessageReceivedListenerForVisibleActivity(
            this,
            object : PushNotificationReceivedListener {
                override fun onMessageReceived(remoteMessage: RemoteMessage) {
                    val imageUrl = remoteMessage.data[imageUrlKey]
                    if (!isNullOrEmpty(imageUrl)) {
                        val uiHandler = Handler(Looper.getMainLooper())
                        uiHandler.post {
                            Picasso.get()
                                .load(imageUrl)
                                .into(binding.imageView)
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
}