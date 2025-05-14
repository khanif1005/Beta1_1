package com.example.beta1_1.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beta1_1.R
import com.example.beta1_1.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity(), View.OnClickListener{

    lateinit var auth : FirebaseAuth

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener(this)
    }


    override fun onClick(p: View?) {
        when (p?.id) {
            R.id.btn_register -> {

                val name = binding.tfName.editText?.text?.toString()?.trim() ?: ""
                val email = binding.tfEmailRegister.editText?.text?.toString()?.trim() ?: ""
                val password = binding.tfPasswordRegister.editText?.text?.toString()?.trim() ?: ""
                val confPassword = binding.tfConfPassword.editText?.text?.toString()?.trim() ?: ""

                if (name.isEmpty()){
                    binding.tfName.error = "Nama Harus Diisi"
                    binding.tfName.requestFocus()
                    return
                }

                if (email.isEmpty()){
                    binding.tfEmailRegister.error = "Email Harus Diisi"
                    binding.tfEmailRegister.requestFocus()
                    return
                }

                if (password.isEmpty()){
                    binding.tfPasswordRegister.error = "Password Harus Diisi"
                    binding.tfPasswordRegister.requestFocus()
                    return
                }

                if (confPassword.isEmpty()){
                    binding.tfConfPassword.error = "Password Harus Diisi"
                    binding.tfConfPassword.requestFocus()
                    return
                }

                if (password != confPassword) {
                    binding.tfConfPassword.error = "Password harus sama"
                    return
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.tfEmailRegister.error = "Email Tidak Valid"
                    binding.tfEmailRegister.requestFocus()
                    return
                }

                if (password.length < 6) {
                    binding.tfPasswordRegister.error = "Password Minimal 6 Karakter"
                    binding.tfPasswordRegister.requestFocus()
                    return
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val userData = hashMapOf(
                                "name" to name,
                                "email" to email,
                                "createdAt" to FieldValue.serverTimestamp()
                            )

                            user?.let {
                                db.collection("users")
                                    .document(it.uid)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, LoginActivity::class.java))
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this, "eror saving user data: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }

                         } else {
                            Toast.makeText(this, "Gagal mendaftar: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }

                    }
            }
        }
    }


}