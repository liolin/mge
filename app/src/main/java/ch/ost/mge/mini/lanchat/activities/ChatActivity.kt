package ch.ost.mge.mini.lanchat.activities

import android.content.Context
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
    private lateinit var btnBack: Button
    private lateinit var btnSend: Button
    private lateinit var txtMessage: EditText
    private lateinit var username: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setupUI()

        username = SettingsStore.username
        webSocketClient = WebSocketClient.create(URI("ws://${SettingsStore.serverAddress}:9000"), ::displayMessage)
        webSocketClient.connect()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    private fun setupUI() {
        btnBack = findViewById(R.id.btnChatBackToHome)
        btnSend = findViewById(R.id.btnSend)
        txtMessage = findViewById(R.id.txtMessage)


        val goToHomeIntend = MainActivity.createIntent(this)
        btnBack.setOnClickListener { startActivity(goToHomeIntend) }
        btnSend.setOnClickListener {
            val message = Message(username, txtMessage.text.toString())
            MessageRepository.addMessage(message)
            webSocketClient.send("$username:${txtMessage.text}")
            txtMessage.setText("")
        }

        adapter = MessageAdapter(MessageRepository.getMessages())
        recyclerView = findViewById(R.id.viewChat)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun displayMessage(message: String?) {
        val dataFromServer = message?.split(':')
        if (dataFromServer != null) {
            val message = Message(dataFromServer[0], dataFromServer[1])
            MessageRepository.addMessage(message)
        }
    }

    companion object Factory {
        fun createIntent(context: Context): Intent {
            return Intent(context, ChatActivity::class.java)
        }
    }
}