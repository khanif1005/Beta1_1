package com.example.beta1_1.Activity

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beta1_1.R
import com.example.beta1_1.databinding.ActivityMaterialVideoBinding
import java.net.URL

class MaterialVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaterialVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bab = intent.getStringExtra("EXTRA_BAB")
        val materiName = intent.getStringExtra("EXTRA_MATERI_NAME")
        val youtube = intent.getStringExtra("EXTRA_YOUTUBE")

        binding.tvMaterialvideoBab.text = bab
        binding.tvMaterialvideoTitle.text = materiName

        setupBackButton()
        loadYoutubeVideo(youtube)
    }

    private fun loadYoutubeVideo(youtube: String?) {
        if (youtube.isNullOrEmpty()) {
            Toast.makeText(this, "Link video tidak tersedia", Toast.LENGTH_SHORT).show()
            return
        }

        binding.wvVideo.settings.javaScriptEnabled = true
        binding.wvVideo.settings.domStorageEnabled = true
        binding.wvVideo.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }

        binding.wvVideo.loadUrl(youtube)
    }

    private fun setupBackButton() {
        binding.icBackMaterialVideo.setOnClickListener{
            finish()
        }
    }
}