package com.example.beta1_1.Activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beta1_1.Adapter.NahwuQuizAdapter
import com.example.beta1_1.DataClass.NahwuQuestions
import com.example.beta1_1.R
import com.example.beta1_1.databinding.ActivityQuestionsBinding

class QuestionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionsBinding
    private lateinit var adapter: NahwuQuizAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bab = intent.getStringExtra("EXTRA_BAB")
        val materiName = intent.getStringExtra("EXTRA_MATERI_NAME")
        val questions = intent.getParcelableArrayListExtra <NahwuQuestions>("QUESTIONS")

        binding.tvQuestions.text = bab
        binding.tvQuestionsTitle.text = materiName

        setupRecylerView(questions ?: emptyList())
        setupSubmitButton()

    }

    private fun setupSubmitButton() {
        binding.btnSubmitQuiz.setOnClickListener{
            val score = adapter.calculateScore()
            val totalQuestions = adapter.itemCount
            showResultDialog(score, totalQuestions)
        }
    }

    private fun showResultDialog(score: Int, total: Int) {
        AlertDialog.Builder(this)
            .setTitle("Hasil Kuis")
            .setMessage("Skor Anda: $score/$total")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

    private fun setupRecylerView(questions: List<NahwuQuestions>) {
        Log.d("cek kuis", "error: ${questions}")
        adapter = NahwuQuizAdapter(questions)
        binding.rvQuestions.apply {
            layoutManager = LinearLayoutManager(this@QuestionsActivity)
            adapter = this@QuestionsActivity.adapter
        }
    }
}