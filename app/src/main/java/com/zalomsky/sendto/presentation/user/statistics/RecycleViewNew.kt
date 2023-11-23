package com.zalomsky.sendto.presentation.user.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zalomsky.sendto.R
import com.zalomsky.sendto.domain.model.AddressBook
import com.zalomsky.sendto.presentation.ColorClass
import com.zalomsky.sendto.presentation.user.clients.adapter.RecycleViewAdapter
import kotlin.random.Random

class RecycleViewNew(
    private val list: ArrayList<AddressBook>
): RecyclerView.Adapter<RecycleViewNew.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.new_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBook = list[position]
        holder.chartPieceName.text = currentBook.name
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val chartPieceName: TextView = itemView.findViewById(R.id.chartPieceName)
    }
}