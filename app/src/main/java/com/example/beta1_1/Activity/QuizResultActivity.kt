package com.example.beta1_1.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beta1_1.DataClass.Questions
import com.example.beta1_1.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class QuizResultActivity : AppCompatActivity() {

    private lateinit var userId: String
    private var materiName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        // Handle konversi kembali ke Int jika diperlukan
        val stringAnswers = intent.getSerializableExtra("USER_ANSWERS") as? HashMap<String, Int> ?: hashMapOf()
        val userAnswers = stringAnswers.mapKeys { it.key.toInt() } as HashMap<Int, Int>

        val score = intent.getIntExtra("EXTRA_SCORE", 0)
        val questions = intent.getParcelableArrayListExtra<Questions>("QUESTIONS") ?: arrayListOf()

        materiName = intent.getStringExtra("EXTRA_MATERI_NAME")
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        saveQuizResultToFirestore(userId, score, userAnswers)


        findViewById<TextView>(R.id.tv_point).text = score.toString()
        findViewById<TextView>(R.id.tv_point_max).text = "/100"

        findViewById<ImageView>(R.id.ic_back_questions_result).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.btn_discussion).setOnClickListener {
            Intent(this, QuestionsDiscussionActivity::class.java).apply {
                putExtra("USER_ANSWERS", userAnswers)
                putExtra("EXTRA_MATERI_NAME", materiName)
                putParcelableArrayListExtra("QUESTIONS", questions)
                startActivity(this)
            }
        }

    }

    private fun saveQuizResultToFirestore(
        userId: String,
        score: Int,
        userAnswers: HashMap<Int, Int>
    ) {
        val db = FirebaseFirestore.getInstance()

        // Konversi key Int ke String
        val convertedAnswers = userAnswers.mapKeys { it.key.toString() }

        val quizResult = hashMapOf(
            "timestamp" to FieldValue.serverTimestamp(),
            "score" to score,
            "answers" to convertedAnswers
        )

        db.collection("users")
            .document(userId)
            .collection("quiz_history")
            .add(quizResult)
            .addOnSuccessListener {
                Log.d("Firestore", "Quiz result saved")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error saving quiz", e)
            }
    }
}