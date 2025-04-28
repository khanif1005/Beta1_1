package com.example.beta1_1.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beta1_1.Activity.MaterialDetailActivity.Companion.MATERI_NAME
import com.example.beta1_1.Activity.MaterialDetailActivity.Companion.QUIZ_COLLECTION
import com.example.beta1_1.Adapter.NahwuListAdapter
import com.example.beta1_1.DataClass.MaterialNahwuList
import com.example.beta1_1.databinding.ActivityNahwuListBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query

class NahwuListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNahwuListBinding
    private lateinit var db: FirebaseFirestore
    private var listenerRegistration: ListenerRegistration? = null

    // Dua adapter untuk dua RecyclerView
    private lateinit var adapterBeforeUTS: NahwuListAdapter
    private lateinit var adapterAfterUTS: NahwuListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNahwuListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        setupFirestore()
        setupBackButton()
    }

    private fun setupRecyclerViews() {


        // Initialize kedua adapter
        adapterBeforeUTS = NahwuListAdapter(context = this,ArrayList(), ArrayList(), object : NahwuListAdapter.OnItemClickListener {
            override fun onItemClick(material: MaterialNahwuList) {
                navigateToDetail(material)
            }
        })
        adapterAfterUTS = NahwuListAdapter(context = this, ArrayList(),ArrayList(), object : NahwuListAdapter.OnItemClickListener {
            override fun onItemClick(material: MaterialNahwuList) {
                navigateToDetail(material)
            }
        })

        // Setup RecyclerView sebelum UTS
        binding.rvMaterialnahwuBeforeUts.apply {
            layoutManager = LinearLayoutManager(this@NahwuListActivity)
            adapter = adapterBeforeUTS
            setHasFixedSize(true)
        }

//      true  // Setup RecyclerView setelah UTS
//        binding.rvMaterialnahwuAfterUts.apply {
//            layoutManager = LinearLayoutManager(this@NahwuListActivity)
//            adapter = adapterAfterUTS
//            setHasFixedSize(true)
//        }
    }

    private fun navigateToDetail(material: MaterialNahwuList) {

        //TODO SETUP EXAM HERE


        val intent = Intent (this, MaterialDetailActivity::class.java).apply {
            putExtra("EXTRA_BAB", material.bab)
            putExtra("EXTRA_MATERI_NAME", material.materialName)
            putExtra("EXTRA_YOUTUBE", material.youtube)
            putExtra("EXTRA_MATERI_ID", material.quiz_id)
            putExtra("EXTRA_DOCUMENT_ID", material.document_id)
            putExtra("EXTRA_MATERI", material.materi)
            putExtra(MATERI_NAME, "materialNahwuList")
            putExtra(QUIZ_COLLECTION, "nahwuQuizzes")
        }
        startActivity(intent)
    }

    private fun setupFirestore() {

        db = FirebaseFirestore.getInstance()
        listenerRegistration = db.collection("materialNahwuList")
            .orderBy("orderField", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show()
                    Log.e("Firestore Error", error.message.toString())
                    return@addSnapshotListener
                }

                val allMaterials = ArrayList<MaterialNahwuList>()
                val isExam = ArrayList<Boolean>()
                value?.documents?.forEach { document ->
                    Log.d("documentNahwu", document.toString())
                    try {
                        val material = document.toObject(MaterialNahwuList::class.java)
                        val rawIsExam = document.getBoolean("isExam")
                        material?.let { allMaterials.add(it) }
                        rawIsExam?.let { isExam.add(it) }
                    } catch (e: Exception) {
                        Log.e("Conversion Error", "Error in doc ${document.id}: ${e.message}")
                    }
                }

                // Update data ke adapter
                adapterBeforeUTS.updateData(allMaterials,isExam)
            }
    }

    private fun setupBackButton() {
        binding.icBackNahwuList.setOnClickListener {
            finish()
        }
    }




}