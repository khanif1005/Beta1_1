package com.example.beta1_1.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beta1_1.Adapter.SharafListAdapter
import com.example.beta1_1.DataClass.MaterialSharafList
import com.example.beta1_1.databinding.ActivitySharafListBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import java.util.ArrayList

class SharafListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySharafListBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var sharafListAdapter: SharafListAdapter
    private var listenerRegistration: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharafListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        setupFirestore()
        setupBackButton()

    }

    private fun setupRecyclerViews() {

        sharafListAdapter = SharafListAdapter(ArrayList(), object : SharafListAdapter.OnItemClickListener {
            override fun onItemClick(material : MaterialSharafList) {
                navigateToDetail(material)
            }
        })

        binding.rvSharafList.apply {
            layoutManager = LinearLayoutManager(this@SharafListActivity)
            adapter = sharafListAdapter
            setHasFixedSize(true)
        }

    }

    private fun navigateToDetail(material: MaterialSharafList) {
        val intent = Intent(this, MaterialDetailActivity::class.java).apply {
            putExtra("EXTRA_BAB", material.bab)
            putExtra("EXTRA_MATERI_NAME", material.materialName)
        }
        startActivity(intent)
    }

    private fun setupFirestore() {
        db = FirebaseFirestore.getInstance()
        listenerRegistration = db.collection("materialSharafList")
            .orderBy("orderField", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show()
                    Log.e("Firestore Error", error.message.toString())
                    return@addSnapshotListener
                }

                val allMaterials = ArrayList<MaterialSharafList>()
                value?.documents?.forEach { document ->
                    try {
                        val material = document.toObject(MaterialSharafList::class.java)
                        material?.let { allMaterials.add(it) }
                    } catch (e: Exception) {
                        Log.e("Conversion Error", "Error in doc ${document.id}: ${e.message}")
                    }
                }

                //kirim data yang sudah diambil
                sharafListAdapter.updateData(allMaterials)
                Log.d("Firestore Debug", "Data loaded: ${allMaterials.size} items")
            }
    }

    private fun setupBackButton() {
        binding.icBackSharafList.setOnClickListener {
            finish()
        }
    }
}