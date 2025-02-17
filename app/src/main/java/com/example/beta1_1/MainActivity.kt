package com.example.beta1_1

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beta1_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNahwu.setOnClickListener(this)
        binding.btnSharaf.setOnClickListener(this)
    }

    override fun onClick (p: View?) {
        when (p?.id) {
            R.id.btn_nahwu -> {
                val moveIntent = Intent(this@MainActivity, NahwuListActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.btn_sharaf -> {
                val moveIntent = Intent(this@MainActivity,SharafListActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }
}