package ch.ost.mge.mini.lanchat.services

import android.util.Log
import java.lang.Exception
import java.net.URI

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.ConnectException

class WebSocketClient(uri: URI, val handleMessage: (message: String?) -> Unit, val onNoConnection: (ex: ConnectException) -> Unit) : WebSocketClient(uri) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d("WebSocketClient", "Connection opened")
    }

    override fun onMessage(message: String?) {
        Log.d("WebSocketClient", "Message received: $message")
        handleMessage(message)
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d("WebSocketClient", "Connection closed $reason")
    }

    override fun onError(ex: Exception?) {
        Log.d("WebSocketClient", "Oh no, something went wrong: ${ex.let { it.toString() }}")

        if (ex is ConnectException) {
            onNoConnection(ex)
        }
    }

    companion object Factory {
        fun create(uri: URI, handleMessage: (message: String?) -> Unit, onNoConnection: (ex: ConnectException) -> Unit): ch.ost.mge.mini.lanchat.services.WebSocketClient {
            return WebSocketClient(uri, handleMessage, onNoConnection)
        }
    }
}