package com.example.beta1_1.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beta1_1.DataClass.MaterialSharafList
import com.example.beta1_1.R

class SharafListAdapter(
    private val sharafList: ArrayList<MaterialSharafList>,
    private val isExams: ArrayList<Boolean>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<SharafListAdapter.SharafViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick (material: MaterialSharafList, isExams : Boolean)
    }

    fun updateData(newData: ArrayList<MaterialSharafList>,exams: ArrayList<Boolean>) {
        sharafList.clear()
        isExams.clear()
        isExams.addAll(exams)
        sharafList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharafViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_material_list, parent, false)
        return SharafViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: SharafViewHolder, position: Int) {

        val materialSharafList = sharafList[position]
        val isExam = isExams[position]
        holder.bab.text = materialSharafList.bab
        holder.materialName.text = materialSharafList.materialName

        if(position == 0 || isExams[position-1]){
            holder.title.visibility = View.VISIBLE
            holder.title.text = "Materi"
        }else if(isExam){
            holder.title.visibility = View.VISIBLE
        holder.title.text = "Ujian"
        }else {
            holder.title.visibility = View.GONE
        }

        holder.itemView.setOnClickListener{
            listener.onItemClick(materialSharafList, isExam)
        }

    }

    override fun getItemCount(): Int {
        return sharafList.size
    }

    class SharafViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bab: TextView = itemView.findViewById(R.id.tv_material_title)
        val materialName: TextView = itemView.findViewById(R.id.tv_material_desc)
        val title : TextView  = itemView.findViewById(R.id.tv_title)
    }

}