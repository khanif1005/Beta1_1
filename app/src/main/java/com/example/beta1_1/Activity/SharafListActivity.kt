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
import com.example.beta1_1.Adapter.SharafListAdapter
import com.example.beta1_1.DataClass.MaterialNahwuList
import com.example.beta1_1.DataClass.MaterialSharafList
import com.example.beta1_1.DataClass.Quiz
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

        sharafListAdapter = SharafListAdapter(ArrayList(), ArrayList(), object : SharafListAdapter.OnItemClickListener {
            override fun onItemClick(material: MaterialSharafList, isExams : Boolean) {
                navigateToDetail(material,isExams )
            }
        })

        binding.rvSharafList.apply {
            layoutManager = LinearLayoutManager(this@SharafListActivity)
            adapter = sharafListAdapter
            setHasFixedSize(true)
        }

    }

    private fun navigateToDetail(material: MaterialSharafList, isExams : Boolean) {
        if(isExams){
            db.collection("sharafQuizzes")
                .document(material.quiz_id ?: "")
                .get()
                .addOnSuccessListener { quizDoc ->
                    if (!quizDoc.exists()) {
                        Toast.makeText(this, "Kuis tidak ditemukan", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }

                    val quiz = quizDoc.toObject(Quiz::class.java)
                    quiz?.questions?.let { questions ->
                        val intent = Intent(this, QuestionsActivity::class.java).apply {
                            putExtra("EXTRA_BAB", material.bab)
                            putExtra("EXTRA_MATERI_NAME",material.materialName)
                            putExtra("EXTRA_DOCUMENT_ID", material.document_id)
                            putParcelableArrayListExtra("QUESTIONS", ArrayList(questions))
                        }
                        startActivity(intent)
                    }
                }
        }else{
            val intent = Intent(this, MaterialDetailActivity::class.java).apply {
                putExtra("EXTRA_BAB", material.bab)
                putExtra("EXTRA_MATERI_NAME", material.materialName)
                putExtra("EXTRA_YOUTUBE", material.youtube)
                putExtra("EXTRA_MATERI_ID", material.quiz_id)
                putExtra("EXTRA_DOCUMENT_ID", material.document_id)
                putExtra("EXTRA_MATERI", material.materi)
                putExtra(MATERI_NAME, "materialSharafList")
                putExtra(QUIZ_COLLECTION, "sharafQuizzes")
            }
            startActivity(intent)
        }
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
                val isExam = ArrayList<Boolean>()
                value?.documents?.forEach { document ->
                    try {
                        val material = document.toObject(MaterialSharafList::class.java)
                        val rawIsExam = document.getBoolean("isExam")
                        rawIsExam?.let { isExam.add(it) }
                        material?.let { allMaterials.add(it) }
                    } catch (e: Exception) {
                        Log.e("Conversion Error", "Error in doc ${document.id}: ${e.message}")
                    }
                }
                //kirim data yang sudah diambil
                sharafListAdapter.updateData(allMaterials, isExam)
                Log.d("Firestore Debug", "Data loaded: ${allMaterials.size} items")
            }
    }

    private fun setupBackButton() {
        binding.icBackSharafList.setOnClickListener {
            finish()
        }
    }
}