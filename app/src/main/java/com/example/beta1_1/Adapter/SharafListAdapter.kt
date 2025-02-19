package com.example.beta1_1.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beta1_1.DataClass.MaterialSharafList
import com.example.beta1_1.R

class SharafListAdapter(private val sharafList: ArrayList<MaterialSharafList>) : RecyclerView.Adapter<SharafListAdapter.SharafViewHolder>() {

    fun updateData(newData: ArrayList<MaterialSharafList>) {
        sharafList.clear()
        sharafList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharafViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_material_list, parent, false)
        return SharafViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: SharafViewHolder, position: Int) {

        val materialSharafList = sharafList[position]
        holder.bab.text = materialSharafList.bab
        holder.materialName.text = materialSharafList.materialName

    }

    override fun getItemCount(): Int {
        return sharafList.size
    }

    class SharafViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bab: TextView = itemView.findViewById(R.id.tv_material_title)
        val materialName: TextView = itemView.findViewById(R.id.tv_material_desc)
    }

}