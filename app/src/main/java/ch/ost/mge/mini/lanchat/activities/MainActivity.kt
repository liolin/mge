package ch.ost.mge.mini.lanchat.activities

import android.content.Context
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

        val chatButton = findViewById<Button>(R.id.btnGoToChat)
        val settingsButton = findViewById<Button>(R.id.btnGoToSettings)
        chatButton.setOnClickListener { startActivity(ChatActivity.createIntent(this)) }
        settingsButton.setOnClickListener { startActivity(SettingsActivity.createIntent(this)) }
    }

    companion object Factory {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}