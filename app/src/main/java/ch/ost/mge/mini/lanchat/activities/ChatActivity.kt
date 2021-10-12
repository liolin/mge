package ch.ost.mge.mini.lanchat.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import java.net.URI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.ost.mge.mini.lanchat.adapter.MessageAdapter
import ch.ost.mge.mini.lanchat.R
import ch.ost.mge.mini.lanchat.WebSocketClient


class ChatActivity : AppCompatActivity() {
    private lateinit var webSocketClient: WebSocketClient
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnBack: Button
    private lateinit var btnSend: Button
    private lateinit var txtMessage: EditText
    private lateinit var username: String
    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setupUI()

        username = intent.extras?.getString("username").toString()
        webSocketClient = newWebSocketClient()
        webSocketClient.connect()

    }

    private fun setupUI() {
        btnBack = findViewById(R.id.btnChatBackToHome)
        btnSend = findViewById(R.id.btnSend)
        txtMessage = findViewById(R.id.txtMessage)


        val goToHomeIntend = Intent(this, MainActivity::class.java)
        btnBack.setOnClickListener { startActivity(goToHomeIntend) }
        btnSend.setOnClickListener {
            val message = txtMessage.text.toString()
            adapter.addMessage(username, message)
            webSocketClient.send("$username:${txtMessage.text.toString()}")
            txtMessage.setText("")
        }

        recyclerView = findViewById(R.id.viewChat)
        val layoutManager: RecyclerView.LayoutManager
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = MessageAdapter()
        recyclerView.adapter = adapter
    }

    private fun newWebSocketClient(): WebSocketClient {
        val uri = URI("ws://192.168.1.15:9000")
        return WebSocketClient(uri, ::displayMessage)
    }

    private fun displayMessage(message: String?) {
        val dataFromServer = message?.split(':')
        if (dataFromServer != null) {
            adapter.addMessage(dataFromServer[0], dataFromServer[1])
        }
    }
}