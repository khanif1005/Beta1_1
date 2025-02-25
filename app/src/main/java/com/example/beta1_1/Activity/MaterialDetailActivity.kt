package com.example.beta1_1.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beta1_1.R
import com.example.beta1_1.databinding.ActivityMaterialDetailBinding

class MaterialDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaterialDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bab = intent.getStringExtra("EXTRA_BAB")
        val materiName = intent.getStringExtra("EXTRA_MATERI_NAME")
        val youtube = intent.getStringExtra("EXTRA_YOUTUBE")

        binding.tvMaterialdetailBab.text = bab
        binding.tvMaterialdetailTitle.text = materiName

        setupBackButton()
        setupWatchVideoButton(youtube, bab, materiName)
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

    private fun setupBackButton() {
        binding.icBackMaterialDetail.setOnClickListener{
            finish()
        }
    }

}