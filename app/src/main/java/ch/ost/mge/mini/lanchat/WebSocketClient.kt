package ch.ost.mge.mini.lanchat

import android.util.Log
import java.lang.Exception
import java.net.URI

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake

class WebSocketClient(uri: URI, val handleMessage: (message: String?) -> Unit) : WebSocketClient(uri) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d("WebSocketClient", "Connection opened")
    }

    override fun onMessage(message: String?) {
        Log.d("WebSocketClient", "Message received: $message")
        handleMessage(message)
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d("WebSocketClient", "Connection closed")
    }

    override fun onError(ex: Exception?) {
        Log.d("WebSocketClient", "Oh no, something went wrong: ${ex.let { it.toString() }}")
    }
}