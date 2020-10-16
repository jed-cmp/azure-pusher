package jed.cmp.azurepusher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pusher.pushnotifications.PushNotifications


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushNotifications.start(applicationContext, "04374500-fb5e-41b6-9bbe-9acb3ee2e174")
        PushNotifications.addDeviceInterest("ethereum-gas-prices")
        PushNotifications.addDeviceInterest("alpaca-trading-bot")
        setContentView(R.layout.activity_main)
    }
}