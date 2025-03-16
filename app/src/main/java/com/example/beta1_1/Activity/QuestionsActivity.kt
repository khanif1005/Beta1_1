package com.example.beta1_1.Activity

import android.content.Intent
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
    private lateinit var questions: ArrayList<NahwuQuestions>
    private var materiName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questions = intent.getParcelableArrayListExtra<NahwuQuestions>("QUESTIONS") ?: arrayListOf()

        val bab = intent.getStringExtra("EXTRA_BAB")
        materiName = intent.getStringExtra("EXTRA_MATERI_NAME")


        binding.tvQuestions.text = bab
        binding.tvQuestionsTitle.text = materiName

        setupRecylerView(questions ?: emptyList())
        setupSubmitButton()
        setupBackButton()

    }

    private fun setupSubmitButton() {
        binding.btnSubmitQuiz.setOnClickListener {
            val score = adapter.calculateScore()
            val userAnswers = adapter.getUserAnswers()

            Intent(this@QuestionsActivity, QuizResultActivity::class.java).apply {
                putExtra("EXTRA_SCORE", score)
                putExtra("EXTRA_TOTAL", 100)
                putExtra("EXTRA_MATERI_NAME", materiName)
                putParcelableArrayListExtra("QUESTIONS", ArrayList(questions))

                // Konversi ke HashMap<String, Int>
                val stringKeyMap = userAnswers.mapKeys { it.key.toString() } as HashMap<String, Int>
                putExtra("USER_ANSWERS", stringKeyMap)

                startActivity(this)
            }
            finish()
        }
    }


    private fun setupRecylerView(questions: List<NahwuQuestions>) {
        Log.d("cek kuis", "error: ${questions}")
        adapter = NahwuQuizAdapter(questions)
        binding.rvQuestions.apply {
            layoutManager = LinearLayoutManager(this@QuestionsActivity)
            adapter = this@QuestionsActivity.adapter
        }
    }

    private fun setupBackButton() {
        binding.icBackQuestions.setOnClickListener{
            finish()
        }
    }
}