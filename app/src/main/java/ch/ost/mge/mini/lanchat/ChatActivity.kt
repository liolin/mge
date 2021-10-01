package ch.ost.mge.mini.lanchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val goToHomeIntend = Intent(this, MainActivity::class.java)
        val btnBack = findViewById<Button>(R.id.btnBackToHome)
        btnBack.setOnClickListener { startActivity(goToHomeIntend) }
    }
}