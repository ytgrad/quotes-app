package com.example.quotes_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.quotes_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var quoteViewModel: QuoteViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        quoteViewModel = ViewModelProvider(this, QuoteViewModelFactory(application))[QuoteViewModel::class.java]
        binding.tvQuote.text = quoteViewModel.getQuote().text
        binding.tvAuthor.text = quoteViewModel.getQuote().author.split(",")[0]

        binding.btnNext.setOnClickListener {
            val quote = quoteViewModel.nextQuote()
            binding.tvQuote.text = quote.text
            binding.tvAuthor.text = quote.author.split(",")[0]
        }

        binding.btnPrev.setOnClickListener {
            val quote = quoteViewModel.previousQuote()
            binding.tvQuote.text = quote.text
            binding.tvAuthor.text = quote.author.split(",")[0]
        }

        binding.btnFloating.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, quoteViewModel.getQuote().text)
            startActivity(intent)
        }

    }
}