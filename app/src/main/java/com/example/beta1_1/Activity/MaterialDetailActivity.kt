package com.example.beta1_1.Activity

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beta1_1.R

class MaterialDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_detail)

        val bab = intent.getStringExtra("EXTRA_BAB")
        val materiName = intent.getStringExtra("EXTRA_MATERI_NAME")

        findViewById<TextView>(R.id.tv_materialdetail_bab).text = bab
        findViewById<TextView>(R.id.tv_materialdetail_title).text = materiName
    }
}