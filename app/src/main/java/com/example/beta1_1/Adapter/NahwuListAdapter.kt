package com.example.beta1_1.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beta1_1.DataClass.MaterialNahwuList
import com.example.beta1_1.R

class NahwuListAdapter(private val nahwuList: ArrayList<MaterialNahwuList>) : RecyclerView.Adapter<NahwuListAdapter.NahwuViewHolder>() {

    fun updateData(newData: ArrayList<MaterialNahwuList>) {
        nahwuList.clear()
        nahwuList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NahwuViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_material_list, parent, false)
        return NahwuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NahwuViewHolder, position: Int) {
        val materialNahwuList = nahwuList[position]
        holder.bab.text = materialNahwuList.bab
        holder.materialName.text = materialNahwuList.materialName
    }

    override fun getItemCount(): Int {
        return nahwuList.size
    }

    class NahwuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bab: TextView = itemView.findViewById(R.id.tv_material_title)
        val materialName: TextView = itemView.findViewById(R.id.tv_material_desc)
    }
}