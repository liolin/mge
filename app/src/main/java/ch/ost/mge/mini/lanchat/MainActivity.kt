package ch.ost.mge.mini.lanchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val switchToChatActivityIntend = Intent(this, ChatActivity::class.java)
        val switchToSettingsActivity = Intent(this, SettingsActivity::class.java);
        val chatButton = findViewById<Button>(R.id.btnGoToChat);
        val settingsButton = findViewById<Button>(R.id.btnGoToSettings)
        chatButton.setOnClickListener { startActivity(switchToChatActivityIntend) }
        settingsButton.setOnClickListener { startActivity(switchToSettingsActivity) }
    }
}