package ch.ost.mge.mini.lanchat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.ost.mge.mini.lanchat.model.Message
import kotlin.collections.ArrayList

class MessageAdapter : RecyclerView.Adapter<MessageViewHolder>() {
    private val dataset: ArrayList<Message> = ArrayList()


    fun addMessage(name: String, message: String) {
        dataset.add(Message(name, message))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                android.R.layout.simple_list_item_2,
                parent,
                false)

        val messageView = view.findViewById<TextView>(android.R.id.text1);
        val userView = view.findViewById<TextView>(android.R.id.text2);
        return MessageViewHolder(view, messageView, userView);
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = dataset[position]
        holder.messageView.text = message.message;
        holder.userView.text = message.username;
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}