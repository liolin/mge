package ch.ost.mge.mini.lanchat.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import ch.ost.mge.mini.lanchat.R
import ch.ost.mge.mini.lanchat.WebSocketClient
import ch.ost.mge.mini.lanchat.model.SettingsStore




class SettingsActivity : AppCompatActivity() {
    private lateinit var btnBack: Button
    private lateinit var btnSave: Button
    private lateinit var txtServerAddress: EditText
    private lateinit var txtUsername: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchToHomeActivity = MainActivity.createIntent(this)
        btnBack = findViewById(R.id.btnSettingsBackToHome)
        btnSave = findViewById(R.id.btnSave)
        txtServerAddress = findViewById(R.id.txtServerAddress)
        txtUsername = findViewById(R.id.txtUsername)

        btnBack.setOnClickListener { startActivity(switchToHomeActivity) }
        btnSave.setOnClickListener {
            SettingsStore.serverAddress = txtServerAddress.text.toString()
            SettingsStore.username = txtUsername.text.toString()
            SettingsStore.save(this)
        }

    }

    override fun onResume() {
        super.onResume()
        SettingsStore.load(this)
        txtUsername.setText(SettingsStore.username)
        txtServerAddress.setText(SettingsStore.serverAddress)
    }

    companion object Factory {
        fun createIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }
}