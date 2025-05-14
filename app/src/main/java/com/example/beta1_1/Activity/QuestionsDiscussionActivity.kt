package com.example.beta1_1.Activity

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beta1_1.Adapter.QuestionsDiscussionAdapter
import com.example.beta1_1.DataClass.Questions
import com.example.beta1_1.R
import com.example.beta1_1.databinding.ActivityQuestionsBinding
import com.example.beta1_1.databinding.ActivityQuestionsDiscussionBinding
import java.util.ArrayList

class QuestionsDiscussionActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityQuestionsDiscussionBinding
    private var materiName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userAnswers = intent.getSerializableExtra("USER_ANSWERS") as? HashMap<Int, Int> ?: hashMapOf()
        val questions = intent.getParcelableArrayListExtra<Questions>("QUESTIONS") ?: arrayListOf()

        materiName = intent.getStringExtra("EXTRA_MATERI_NAME")

        binding.tvDiscussionsTitle.text = materiName

        setupRecyclerView (questions, userAnswers)

    }

    private fun setupRecyclerView(
        questions: ArrayList<Questions>,
        userAnswers: java.util.HashMap<Int, Int>
    ) {
        val recylerView = binding.rvDiscussion
        recylerView.layoutManager = LinearLayoutManager(this)
        recylerView.adapter = QuestionsDiscussionAdapter(questions, userAnswers)
    }
}