package ch.ost.mge.mini.lanchat.model

class Message(val username: String, val message: String) {

    override fun toString(): String {
        return "$username: $message"
    }
}