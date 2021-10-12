package ch.ost.mge.mini.lanchat.model

object MessageRepository {
    private val data: ArrayList<Message> = ArrayList()

    fun getMessages(): ArrayList<Message> {
        return data
    }

    fun addMessage(message: Message) {
        data.add(message)
    }
}