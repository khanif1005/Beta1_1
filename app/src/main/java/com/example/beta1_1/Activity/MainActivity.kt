package com.example.beta1_1.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.beta1_1.R
import com.example.beta1_1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNahwu.setOnClickListener(this)
        binding.btnSharaf.setOnClickListener(this)
        binding.icProfile.setOnClickListener(this)
    }

    override fun onClick (p: View?) {
        when (p?.id) {
            R.id.btn_nahwu -> {
                val moveIntent = Intent(this@MainActivity, NahwuListActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.btn_sharaf -> {
                val moveIntent = Intent(this@MainActivity, SharafListActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.ic_profile -> {
                val user = FirebaseAuth.getInstance().currentUser
                val db = FirebaseFirestore.getInstance()
                db.collection("users").document(user?.uid ?: "")
                    .get()
                    .addOnSuccessListener { document ->

                        val name = document.getString("name") ?: "User"
                        val email = document.getString("email") ?: ""

                        val intent = Intent(this, ProfileActivity::class.java).apply {
                            putExtra("name", name)
                            putExtra("email", email)
                        }
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error getting document: ${e.message}")
                    }

            }
        }
    }
}