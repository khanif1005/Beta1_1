package com.example.beta1_1.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.beta1_1.DataClass.MaterialNahwuList
import com.example.beta1_1.R

class NahwuListAdapter(
    private val context : Context,
    private val nahwuList: ArrayList<MaterialNahwuList>,
    private val isExams: ArrayList<Boolean>,
    private val listener: OnItemClickListener) : RecyclerView.Adapter<NahwuListAdapter.NahwuViewHolder>()

    {
        interface OnItemClickListener {
            fun onItemClick(material: MaterialNahwuList, isExams : Boolean)
        }

        fun updateData(newData: ArrayList<MaterialNahwuList>,exams: ArrayList<Boolean>) {
            nahwuList.clear()
            isExams.clear()
            isExams.addAll(exams)
            nahwuList.addAll(newData)
            notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NahwuViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_material_list, parent, false)
        return NahwuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NahwuViewHolder, position: Int) {
        val materialNahwuList = nahwuList[position]
        val isExam = isExams[position]
        holder.bab.text = materialNahwuList.bab
        holder.materialName.text = materialNahwuList.materialName
        Log.d("materialName", materialNahwuList.toString())

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
            listener.onItemClick(materialNahwuList, isExam)
        }
    }

    override fun getItemCount(): Int {
        return nahwuList.size
    }

    class NahwuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bab: TextView = itemView.findViewById(R.id.tv_material_title)
        val materialName: TextView = itemView.findViewById(R.id.tv_material_desc)
        val title : TextView  = itemView.findViewById(R.id.tv_title)
        val cardView : CardView = itemView.findViewById(R.id.card_view)
    }
}