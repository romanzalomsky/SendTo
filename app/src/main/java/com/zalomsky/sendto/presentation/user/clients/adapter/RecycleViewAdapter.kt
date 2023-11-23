package com.zalomsky.sendto.presentation.user.clients.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zalomsky.sendto.R
import com.zalomsky.sendto.domain.model.AddressBook

class RecycleViewAdapter (
    private val list: ArrayList<AddressBook>
) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>(){

    private lateinit var listener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        listener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBook = list[position]
        holder.tvAddressBook.text = currentBook.name
/*        val currentBookId = currentBook.id*/
        holder.amountClients.text = currentBook.id
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val tvAddressBook : TextView = itemView.findViewById(R.id.tvAddressBook)
        val amountClients : TextView = itemView.findViewById(R.id.amountClients)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}