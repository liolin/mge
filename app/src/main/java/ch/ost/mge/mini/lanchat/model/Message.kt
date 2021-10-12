package ch.ost.mge.mini.lanchat.model

import com.google.gson.Gson

class Message(val username: String, val message: String) {

    override fun toString(): String {
        return "$username: $message"
    }
}