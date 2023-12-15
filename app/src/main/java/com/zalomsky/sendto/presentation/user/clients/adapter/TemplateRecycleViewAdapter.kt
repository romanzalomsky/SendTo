package com.zalomsky.sendto.presentation.user.clients.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zalomsky.sendto.R
import com.zalomsky.sendto.domain.model.Template

class TemplateRecycleViewAdapter (
    private val templateList: ArrayList<Template>
) : RecyclerView.Adapter<TemplateRecycleViewAdapter.ViewHolder>(){

/*    private lateinit var listener: RecycleViewAdapter.onItemClickListener*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.template_list_item, parent, false)
        return ViewHolder(itemView /*listener*/)
    }

    override fun getItemCount(): Int {
        return templateList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTemplate = templateList[position]
        holder.tvTemplateName.text = currentTemplate.name
/*        holder.amountClients.text = currentTemplate.id*/
    }

    class ViewHolder(itemView: View, /*clickListener: RecycleViewAdapter.onItemClickListener*/) : RecyclerView.ViewHolder(itemView){
        val tvTemplateName : TextView = itemView.findViewById(R.id.tvTemplateName)

/*        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }*/
    }
}