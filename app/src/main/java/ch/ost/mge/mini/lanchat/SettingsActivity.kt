package ch.ost.mge.mini.lanchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchToHomeActivity = Intent(this, MainActivity::class.java)
        val btnBack = findViewById<Button>(R.id.btnSettingsBackToHome)
        btnBack.setOnClickListener { startActivity(switchToHomeActivity) }
    }
}