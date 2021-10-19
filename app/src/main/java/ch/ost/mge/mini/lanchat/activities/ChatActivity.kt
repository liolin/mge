package ch.ost.mge.mini.lanchat.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private lateinit var lblStatusMessage: TextView
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
        notificationSender = NotificationSender(this)
        webSocketClient = WebSocketClient.create(
            URI("ws://${SettingsStore.serverAddress}:9000"),
            MessageRepository::addMessage,
            ::displayNoConnection
        )
        webSocketClient.connect()
        MessageRepository.addObserver(this)

        setupUI()
    }

    override fun onStop() {
        super.onStop()
        webSocketClient.close()
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setupUI() {
        btnBack = findViewById(R.id.btnChatBackToHome)
        btnSend = findViewById(R.id.btnSend)
        txtMessage = findViewById(R.id.txtMessage)
        lblStatusMessage = findViewById(R.id.lblStatusMessage)

        btnBack.setOnClickListener { startActivity(MainActivity.createIntent(this)) }
        btnSend.setOnClickListener { sendMessage() }

        recyclerView = findViewById(R.id.viewChat)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MessageAdapter(MessageRepository.getMessages())
    }

    override fun update(p0: Observable?, data: Any?) {
        if (p0 == MessageRepository) {
            if (data != null) {
                val message = data as Message
                messageReceived(message)
            }
        }
    }

    private fun sendMessage() {
        val message = Message(username, txtMessage.text.toString())
        webSocketClient.send(gson.toJson(message))
        txtMessage.setText("")
    }

    private fun messageReceived(message: Message) {
        runOnUiThread {
            recyclerView.adapter?.notifyItemInserted(MessageRepository.size() - 1)
            notificationSender.sendNotification(this, "New Message", message.message)
        }
    }

    private fun displayNoConnection(ex: java.net.ConnectException) {
        lblStatusMessage.text = "Oh no. No connection to Server: ${SettingsStore.serverAddress}"
    }

}