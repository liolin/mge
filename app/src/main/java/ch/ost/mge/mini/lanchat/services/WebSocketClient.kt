package ch.ost.mge.mini.lanchat.services

import android.util.Log
import java.lang.Exception
import java.net.URI

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.ConnectException

class WebSocketClient(uri: URI, val onOpen: () -> Unit, val onMessageReceived: (message: String?) -> Unit, val onClose: () -> Unit, val onNoConnection: (ex: ConnectException) -> Unit) : WebSocketClient(uri) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d("WebSocketClient", "Connection opened")
        startConnectionLostTimer()
        onOpen()
    }

    override fun onMessage(message: String?) {
        Log.d("WebSocketClient", "Message received: $message")
        onMessageReceived(message)
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d("WebSocketClient", "Connection closed: $reason")
        stopConnectionLostTimer()
        onClose()
    }

    override fun onError(ex: Exception?) {
        Log.d("WebSocketClient", "Oh no, something went wrong: ${ex.let { it.toString() }}")

        if (ex is ConnectException) {
            onNoConnection(ex)
        }
    }

    companion object Factory {
        fun create(uri: URI, onOpen: () -> Unit, onMessage: (message: String?) -> Unit, onClose: () -> Unit, onNoConnection: (ex: ConnectException) -> Unit): ch.ost.mge.mini.lanchat.services.WebSocketClient {
            val client = WebSocketClient(uri, onOpen, onMessage, onClose, onNoConnection)
            client.connectionLostTimeout = 10
            return client
        }
    }
}