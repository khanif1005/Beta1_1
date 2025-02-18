package com.example.beta1_1

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beta1_1.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var auth : FirebaseAuth

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
        binding.tvRegist.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()

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
                            Toast.makeText(this, "Welcome to Nasho Learn! $email", Toast.LENGTH_SHORT).show()
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
