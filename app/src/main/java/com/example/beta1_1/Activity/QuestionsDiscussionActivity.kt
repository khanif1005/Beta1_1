package com.example.beta1_1.Activity

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beta1_1.Adapter.NahwuDiscussionAdapter
import com.example.beta1_1.DataClass.NahwuQuestions
import com.example.beta1_1.R
import java.util.ArrayList

class QuestionsDiscussionActivity : AppCompatActivity() {

    private var materiName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_discussion)

        val userAnswers = intent.getSerializableExtra("USER_ANSWERS") as? HashMap<Int, Int> ?: hashMapOf()
        val questions = intent.getParcelableArrayListExtra<NahwuQuestions>("QUESTIONS") ?: arrayListOf()

        materiName = intent.getStringExtra("EXTRA_MATERI_NAME")

        findViewById<TextView>(R.id.tv_discussions_title).text = materiName

        setupRecyclerView (questions, userAnswers)

    }

    private fun setupRecyclerView(
        questions: ArrayList<NahwuQuestions>,
        userAnswers: java.util.HashMap<Int, Int>
    ) {
        val recylerView = findViewById<RecyclerView>(R.id.rv_discussion)
        recylerView.layoutManager = LinearLayoutManager(this)
        recylerView.adapter = NahwuDiscussionAdapter(questions, userAnswers)
    }
}