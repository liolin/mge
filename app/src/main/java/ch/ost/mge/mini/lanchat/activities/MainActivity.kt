package ch.ost.mge.mini.lanchat.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ch.ost.mge.mini.lanchat.R
import ch.ost.mge.mini.lanchat.model.SettingsStore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SettingsStore.load(this)

        val switchToChatActivityIntend = Intent(this, ChatActivity::class.java)
        val switchToSettingsActivity = Intent(this, SettingsActivity::class.java);
        val chatButton = findViewById<Button>(R.id.btnGoToChat);
        val settingsButton = findViewById<Button>(R.id.btnGoToSettings)
        chatButton.setOnClickListener {
            switchToChatActivityIntend.putExtra("username", "Hans")
            startActivity(switchToChatActivityIntend)
        }
        settingsButton.setOnClickListener { startActivity(switchToSettingsActivity) }
    }
}