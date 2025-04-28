package com.example.beta1_1.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beta1_1.R
import com.example.beta1_1.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding // Sesuaikan dengan nama layout XML

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Terima data dari Intent
        val name = intent.getStringExtra("name") ?: ""
        val email = intent.getStringExtra("email") ?: ""

        // Set data ke view
        binding.tvNameProfile.text = name
        binding.tvEmailProfile.text = email

        binding.icBackProfile.setOnClickListener {
            finish()
        }

        // Handle logout
        binding.cvLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}