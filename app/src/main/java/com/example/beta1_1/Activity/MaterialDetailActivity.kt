package com.example.beta1_1.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beta1_1.DataClass.Questions
import com.example.beta1_1.DataClass.Quiz
import com.example.beta1_1.R
import com.example.beta1_1.databinding.ActivityMaterialDetailBinding
import com.google.firebase.firestore.FirebaseFirestore

class MaterialDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaterialDetailBinding
    private lateinit var currentMateriId : String
    private lateinit var documentId : String
    private var materi: String? = null
    private var bab: String? = null
    private var materiName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        materi = intent.getStringExtra("EXTRA_MATERI")
        bab = intent.getStringExtra("EXTRA_BAB")
        materiName = intent.getStringExtra("EXTRA_MATERI_NAME")
        val youtube = intent.getStringExtra("EXTRA_YOUTUBE")
        currentMateriId = intent.getStringExtra("EXTRA_MATERI_ID") ?: ""
        documentId = intent.getStringExtra("EXTRA_DOCUMENT_ID")?: ""

        binding.tvMaterialdetailBab.text = bab
        binding.tvMaterialdetailTitle.text = materiName
        binding.tvMateriDetail.text = Html.fromHtml(materi, Html.FROM_HTML_MODE_COMPACT)

        binding.btnStartQuiz.setOnClickListener{
            if (documentId.isEmpty()) {
                Toast.makeText(this, "Materi tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loadQuizFromFirestore()
        }

        setupBackButton()
        setupWatchVideoButton(youtube, bab, materiName)

    }

    private fun loadQuizFromFirestore() {

        val materi = intent.getStringExtra(MATERI_NAME)
        val quizColection = intent.getStringExtra(QUIZ_COLLECTION)
        val db = FirebaseFirestore.getInstance()
        db.collection(materi ?: "").document(documentId)
            .get()
            .addOnSuccessListener { materiDoc ->
                if (!materiDoc.exists()) {
                    Toast.makeText(this, "Materi tidak ditemukan", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val quizId = materiDoc.getString("quiz_id") ?: run {
                    Toast.makeText(this, "Kuis tidak tersedia", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                db.collection(quizColection ?: "").document(quizId)
                    .get()
                    .addOnSuccessListener { quizDoc ->
                        Log.d("cek kuis", "error: ${quizDoc}")
                        if (!quizDoc.exists()) {
                            Toast.makeText(this, "Kuis tidak ditemukan", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }

                        val quiz = quizDoc.toObject(Quiz::class.java)
                        Log.d("quiz", quiz?.questions.toString())
                        quiz?.questions?.let { questions ->
                            if (questions.isEmpty()) {
                                Toast.makeText(this, "Kuis Kosong", Toast.LENGTH_SHORT).show()
                            } else {
                                startQuizActivity(questions, bab, materiName)
                            }
                        }
                    }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error: ${e.message}")
            }

    }

    private fun setupWatchVideoButton(youtube: String?, bab: String?, materiName: String?) {
        binding.btnWatchVideo.setOnClickListener{
            if (youtube != null && bab != null && materiName != null) {
                val intent = Intent(this, MaterialVideoActivity::class.java).apply {
                    putExtra("EXTRA_BAB", bab)
                    putExtra("EXTRA_MATERI_NAME", materiName)
                    putExtra("EXTRA_YOUTUBE", youtube)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Video Tidak Tersedia", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startQuizActivity(questions: List<Questions>, bab: String?, materiName: String?) {
        val intent = Intent(this, QuestionsActivity::class.java).apply {
            putExtra("EXTRA_BAB", bab)
            putExtra("EXTRA_MATERI_NAME", materiName)
            putExtra("EXTRA_DOCUMENT_ID", documentId)
            putParcelableArrayListExtra("QUESTIONS", ArrayList(questions))
        }
        startActivity(intent)
    }

    private fun setupBackButton() {
        binding.icBackMaterialDetail.setOnClickListener{
            finish()
        }
    }

    companion object {
        const val MATERI_NAME = "MATERI"
        const val QUIZ_COLLECTION = "QUIZ"
    }

}