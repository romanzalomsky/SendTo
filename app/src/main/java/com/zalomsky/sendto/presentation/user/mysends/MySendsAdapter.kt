package com.zalomsky.sendto.presentation.user.mysends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zalomsky.sendto.R
import com.zalomsky.sendto.domain.model.AddressBook
import com.zalomsky.sendto.domain.model.EmailMessages
import com.zalomsky.sendto.presentation.user.clients.adapter.RecycleViewAdapter

class MySendsAdapter (
    private val list_message: ArrayList<EmailMessages>
) : RecyclerView.Adapter<MySendsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.message_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val messageAB = list_message[position]

        holder.messageAB.text = messageAB.addressBook
        holder.messageText.text = messageAB.message

        val count = messageAB.to?.split(",")?.size
        holder.clients.text = count.toString()
    }

    override fun getItemCount(): Int {
        return list_message.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val messageText : TextView = itemView.findViewById(R.id.tv_message)
        val messageAB : TextView = itemView.findViewById(R.id.tv_address_book)
        val clients : TextView = itemView.findViewById(R.id.amount_сlients)
    }
}