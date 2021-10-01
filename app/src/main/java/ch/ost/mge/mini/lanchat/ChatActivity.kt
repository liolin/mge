package ch.ost.mge.mini.lanchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import java.net.URI

class ChatActivity : AppCompatActivity() {
    private lateinit var webSocketClient: WebSocketClient
    private lateinit var btnBack: Button
    private lateinit var btnSend: Button
    private lateinit var txtMessage: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val goToHomeIntend = Intent(this, MainActivity::class.java)
        webSocketClient = newWebSocketClient()
        Log.d("ChatActivity", "WebSocketClient created: ${webSocketClient.uri}")
        webSocketClient.connect()

        btnBack = findViewById(R.id.btnChatBackToHome)
        btnSend = findViewById(R.id.btnSend)
        txtMessage = findViewById(R.id.txtMessage)

        btnBack.setOnClickListener { startActivity(goToHomeIntend) }
        btnSend.setOnClickListener {
            Log.d("ChatActivity", txtMessage.text.toString())
            webSocketClient.send(txtMessage.text.toString())
        }


    }

    private fun newWebSocketClient(): WebSocketClient {
        Log.d("ChatActivity", "Create new URI")
        val uri = URI("ws://192.168.1.15:9000")
        Log.d("ChatActivity", "Create new WebSocketClient")
        return WebSocketClient(uri, ::displayMessage)
    }

    private fun displayMessage(message: String?) {
        TODO("Not implemented yet")
    }
}