package ch.ost.mge.mini.lanchat.model

import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

object MessageRepository : Observable() {
    private val data: ArrayList<Message> = ArrayList()
    private val gson = Gson()

    init {
        data.add(Message("hansi", "Hallo Welt"))
        data.add(Message("hansi", "ist jemand zu Hause?"))
    }
    fun getMessages(): ArrayList<Message> {
        return data
    }

    fun addMessage(message: Message) {
        data.add(message)
        setChanged();
        notifyObservers(message)
    }

    fun addMessage(message: String?) {
        if (message != null) {
            val message = gson.fromJson(message, Message::class.java)
            addMessage(message)
        }
    }

    fun size(): Int {
        return data.size
    }
}