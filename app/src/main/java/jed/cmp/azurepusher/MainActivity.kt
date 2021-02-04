package jed.cmp.azurepusher

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.RemoteMessage
import com.pusher.pushnotifications.PushNotificationReceivedListener
import com.pusher.pushnotifications.PushNotifications
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushNotifications.start(applicationContext, "04374500-fb5e-41b6-9bbe-9acb3ee2e174")
        PushNotifications.addDeviceInterest("debug-hello")
        PushNotifications.addDeviceInterest("ethereum-gas-prices")
        PushNotifications.addDeviceInterest("alpaca-trading-bot")
        setContentView(R.layout.activity_main)
        Picasso
            .get()
            .load("https://upload.wikimedia.org/wikipedia/commons/thumb/1/10/Supermoon_Nov-14-2016-minneapolis.jpg/1200px-Supermoon_Nov-14-2016-minneapolis.jpg")
            .into(imageView)
    }

    override fun onResume() {
        super.onResume()
        PushNotifications.setOnMessageReceivedListenerForVisibleActivity(
            this,
            object : PushNotificationReceivedListener {
                override fun onMessageReceived(remoteMessage: RemoteMessage) {
                    val imageUrl = remoteMessage.data["image-url"]
                    if (imageUrl != null) {
                        val uiHandler = Handler(Looper.getMainLooper())
                        uiHandler.post {
                            Picasso.get()
                                .load(imageUrl)
                                .into(imageView)
                        }
                    }
                }
            })
    }
}