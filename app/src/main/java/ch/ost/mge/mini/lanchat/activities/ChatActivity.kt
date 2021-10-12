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
import ch.ost.mge.mini.lanchat.model.Message
import ch.ost.mge.mini.lanchat.model.MessageRepository
import ch.ost.mge.mini.lanchat.model.SettingsStore


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
        webSocketClient = WebSocketClient.create(URI("ws://${SettingsStore.serverAddress}:9000"), ::displayMessage)
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

    private fun displayMessage(message: String?) {
        val dataFromServer = message?.split(':')
        if (dataFromServer != null) {
            val message = Message(dataFromServer[0], dataFromServer[1])
            MessageRepository.addMessage(message)
        }
    }
}