package com.example.beta1_1.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beta1_1.DataClass.NahwuQuestions
import com.example.beta1_1.R

class NahwuQuizAdapter (private val questions: List<NahwuQuestions>) :
    RecyclerView.Adapter<NahwuQuizAdapter.ViewHolder>() {

        private val usersAnswers = mutableMapOf<Int, Int>()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvQuestionNumber: TextView = itemView.findViewById(R.id.num_questions)
            val tvQuestion: TextView = itemView.findViewById(R.id.questions)
            val radioGroup: RadioGroup = itemView.findViewById(R.id.rg_options)
            val radioButtons: List<RadioButton> = listOf(
                itemView.findViewById(R.id.rd_options_a),
                itemView.findViewById(R.id.rd_options_b),
                itemView.findViewById(R.id.rd_options_c),
                itemView.findViewById(R.id.rd_options_d)
            )
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_questions, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = questions.size

    fun calculateScore(): Int {
        return questions.mapIndexed { index, question ->
            if (usersAnswers[index] == question.correct_answer) 1 else 0
        }.sum()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]

        // Set nomor pertanyaan
        holder.tvQuestionNumber.text = "No. ${position + 1}"

        // Set pertanyaan
        holder.tvQuestion.text = question.question_text

        // Set opsi jawaban
        question.options.forEachIndexed { index, option ->
            holder.radioButtons[index].text = option.text
        }

        // Handle jawaban user
        holder.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rd_options_a -> usersAnswers[position] = 0
                R.id.rd_options_b -> usersAnswers[position] = 1
                R.id.rd_options_c -> usersAnswers[position] = 2
                R.id.rd_options_d -> usersAnswers[position] = 3
            }
        }
    }


}