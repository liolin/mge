package ch.ost.mge.mini.lanchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import ch.ost.mge.mini.lanchat.SettingsStore;

class SettingsActivity : AppCompatActivity() {
    private val store = SettingsStore()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchToHomeActivity = Intent(this, MainActivity::class.java)
        val btnBack = findViewById<Button>(R.id.btnSettingsBackToHome)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val txtServerAddress = findViewById<EditText>(R.id.txtServerAddress)
        val txtUsername = findViewById<EditText>(R.id.txtUsername)

        btnBack.setOnClickListener { startActivity(switchToHomeActivity) }
        btnSave.setOnClickListener {
            store.serverAddress = txtServerAddress.text.toString()
            store.username = txtUsername.text.toString()
        }
    }
}