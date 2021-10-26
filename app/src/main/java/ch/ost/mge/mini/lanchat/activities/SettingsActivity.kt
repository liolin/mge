package ch.ost.mge.mini.lanchat.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ch.ost.mge.mini.lanchat.R
import ch.ost.mge.mini.lanchat.model.SettingsStore

class SettingsActivity : AppCompatActivity() {
    private lateinit var btnBack: Button
    private lateinit var btnSave: Button
    private lateinit var txtServerAddress: EditText
    private lateinit var txtUsername: EditText

    companion object Factory {
        fun createIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        btnBack = findViewById(R.id.btnSettingsBackToHome)
        btnSave = findViewById(R.id.btnSave)
        txtServerAddress = findViewById(R.id.txtServerAddress)
        txtUsername = findViewById(R.id.txtUsername)

        btnBack.setOnClickListener { startActivity(MainActivity.createIntent(this)) }
        btnSave.setOnClickListener { saveSettings() }

    }

    override fun onResume() {
        super.onResume()
        SettingsStore.load(this)
        txtUsername.setText(SettingsStore.username)
        txtServerAddress.setText(SettingsStore.serverAddress)
    }

    private fun saveSettings() {
        SettingsStore.serverAddress = txtServerAddress.text.toString()
        SettingsStore.username = txtUsername.text.toString()
        SettingsStore.save(this)
        Toast.makeText(this, "Settings saved", Toast.LENGTH_LONG).show()
    }
}