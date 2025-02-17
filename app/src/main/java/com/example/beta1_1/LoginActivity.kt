package com.example.beta1_1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beta1_1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
        binding.tvRegist.setOnClickListener(this)

    }

    override fun onClick(p: View?) {
        when (p?.id) {
            R.id.btn_login -> {
                val moveIntent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.tv_regist -> {
                val moveIntent = Intent (this@LoginActivity, SignUpActivity::class.java)
                startActivity((moveIntent))
            }
        }
    }

}
