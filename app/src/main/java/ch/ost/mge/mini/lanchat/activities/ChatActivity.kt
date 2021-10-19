package ch.ost.mge.mini.lanchat.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import java.net.URI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.ost.mge.mini.lanchat.adapter.MessageAdapter
import ch.ost.mge.mini.lanchat.R
import ch.ost.mge.mini.lanchat.services.WebSocketClient
import ch.ost.mge.mini.lanchat.model.Message
import ch.ost.mge.mini.lanchat.model.MessageRepository
import ch.ost.mge.mini.lanchat.model.SettingsStore
import ch.ost.mge.mini.lanchat.services.NotificationSender
import com.google.gson.Gson
import java.util.*


class ChatActivity : AppCompatActivity(), Observer {
    private lateinit var webSocketClient: WebSocketClient
    private lateinit var btnBack: Button
    private lateinit var btnSend: Button
    private lateinit var txtMessage: EditText
    private lateinit var lblUsername: TextView
    private lateinit var username: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationSender: NotificationSender
    private val gson = Gson()


    companion object Factory {
        fun createIntent(context: Context): Intent {
            return Intent(context, ChatActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        username = SettingsStore.username
        if (!this::webSocketClient.isInitialized) {
            webSocketClient = WebSocketClient.create(
                URI("ws://${SettingsStore.serverAddress}:9000"),
                MessageRepository::addMessage
            )
            webSocketClient.connect()
        }
        notificationSender = NotificationSender(this)
        MessageRepository.addObserver(this)


        setupUI()
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setupUI() {
        btnBack = findViewById(R.id.btnChatBackToHome)
        btnSend = findViewById(R.id.btnSend)
        txtMessage = findViewById(R.id.txtMessage)
        lblUsername = findViewById(R.id.lblChatUsername)

        lblUsername.text = username
        btnBack.setOnClickListener { startActivity(MainActivity.createIntent(this)) }
        btnSend.setOnClickListener {
            val message = Message(username, txtMessage.text.toString())
            webSocketClient.send(gson.toJson(message))
            txtMessage.setText("")
        }

        recyclerView = findViewById(R.id.viewChat)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MessageAdapter(MessageRepository.getMessages())
    }

    override fun update(p0: Observable?, message: Any?) {
        runOnUiThread {
            if (message != null) {
                val message = message as Message
                recyclerView.adapter?.notifyItemInserted(MessageRepository.size() - 1)
                notificationSender.sendNotification(this, "New Message", message.message)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        webSocketClient.close()
    }
}