package ch.ost.mge.mini.lanchat.model

object MessageRepository {
    private val data: ArrayList<Message> = ArrayList()

    init {
        data.add(Message("hansi", "Hallo Welt"))
        data.add(Message("hansi", "ist jemand zu Hause?"))
    }
    fun getMessages(): ArrayList<Message> {
        return data
    }

    fun addMessage(message: Message) {
        data.add(message)
    }

    fun size(): Int {
        return data.size
    }
}