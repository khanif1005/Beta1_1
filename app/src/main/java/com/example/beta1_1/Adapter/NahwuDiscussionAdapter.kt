package com.example.beta1_1.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.beta1_1.DataClass.NahwuQuestions
import com.example.beta1_1.R

class NahwuDiscussionAdapter(
    private val questions: List<NahwuQuestions>,
    private val userAnswer: Map<Int, Int>
) : RecyclerView.Adapter<NahwuDiscussionAdapter.NahwuDiscussionViewHolder>() {

    inner class NahwuDiscussionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNo: TextView = itemView.findViewById(R.id.tv_no_discussion)
        val tvQuestions: TextView = itemView.findViewById(R.id.tv_question_discussion)
        val tvCorrectAnswer: TextView = itemView.findViewById(R.id.tv_right_answer_value)
        val tvUserAnswer: TextView = itemView.findViewById(R.id.tv_answer_discussion)
        val tvCorrectstatus: TextView = itemView.findViewById(R.id.tv_correct_discussion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NahwuDiscussionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_discussion, parent, false)
        return NahwuDiscussionViewHolder(view)
    }

    override fun getItemCount(): Int = questions.size

    override fun onBindViewHolder(holder: NahwuDiscussionViewHolder, position: Int) {
        val question = questions[position]
        val userAnswerIndex = userAnswer[position] ?: 0
        val correctAnswerIndex = question.correct_answer
        val isCorrect = userAnswerIndex == correctAnswerIndex

        // Set nomor pertanyaan
        holder.tvNo.text = "No. ${position + 1}"

        // Set pertanyaan
        holder.tvQuestions.text = question.question_text

        // Set jawaban benar
        val correctAnswer = question.options.getOrNull(correctAnswerIndex)?.text ?: ""
        holder.tvCorrectAnswer.text = correctAnswer

        // Set jawaban user dan status
        handleUserAnswer(holder, question, userAnswerIndex, isCorrect)
    }

    private fun handleUserAnswer(
        holder: NahwuDiscussionViewHolder,
        question: NahwuQuestions,
        userAnswerIndex: Int,
        isCorrect: Boolean
    ) {
        val userAnswerText = question.options.find { it.id == userAnswerIndex }?.text ?: "Tidak dijawab"
        holder.tvUserAnswer.text = userAnswerText

        val context = holder.itemView.context
        if (isCorrect) {
            holder.tvCorrectstatus.apply {
                text = "Benar"
                setTextColor(ContextCompat.getColor(context, R.color.shadeofgreen))
            }

        } else {
            holder.tvCorrectstatus.apply {
                text = "Salah"
                setTextColor(ContextCompat.getColor(context, R.color.red))
            }
        }
    }
}
