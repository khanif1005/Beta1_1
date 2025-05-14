package com.example.beta1_1.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beta1_1.R
import com.example.beta1_1.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
        binding.tvRegist.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            redirectToMainActivity()
            return
        }
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onClick(p: View?) {
        when (p?.id) {
            R.id.btn_login -> {
                val email = binding.tfEmail.editText?.text?.toString()?.trim() ?: ""
                val password = binding.tfPassword.editText?.text?.toString()?.trim() ?: ""

                if (email.isEmpty()){
                    binding.tfEmail.error = "Email Harus Diisi"
                    binding.tfEmail.requestFocus()
                    return
                }

                if (password.isEmpty()){
                    binding.tfPassword.error = "Password Harus Diisi"
                    binding.tfPassword.requestFocus()
                    return
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.tfEmail.error = "Email Tidak Valid"
                    binding.tfEmail.requestFocus()
                    return
                }

                if (password.length < 6) {
                    binding.tfPassword.error = "Password Minimal 6 Karakter"
                    binding.tfPassword.requestFocus()
                    return
                }

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Welcome to TANUSHA! $email", Toast.LENGTH_SHORT).show()
                            val moveIntent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(moveIntent)
                        } else {
                            Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

            R.id.tv_regist -> {
                val moveIntent = Intent (this@LoginActivity, SignUpActivity::class.java)
                startActivity((moveIntent))
            }
        }
    }

}
