package com.example.beta1_1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beta1_1.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener(this)
    }

    override fun onClick(p: View?) {
        when (p?.id) {
            R.id.btn_register -> {
                val moveIntent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }


}