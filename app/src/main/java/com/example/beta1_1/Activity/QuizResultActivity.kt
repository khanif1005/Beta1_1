package com.example.beta1_1.Activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beta1_1.R
import com.google.android.material.button.MaterialButton

class QuizResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_quiz_result)

        val score = intent.getIntExtra("EXTRA_SCORE", 0)


        findViewById<TextView>(R.id.tv_point).text = score.toString()
        findViewById<TextView>(R.id.tv_point_max).text = "/100"

        findViewById<ImageView>(R.id.ic_back_questions_result).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.btn_discussion).setOnClickListener {

        }

    }
}