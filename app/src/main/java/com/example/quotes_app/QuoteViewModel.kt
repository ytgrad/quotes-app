package com.example.quotes_app

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson

class QuoteViewModel(val context: Context): ViewModel() {
    private var quoteList : Array<Quote> = emptyArray()
    private var index = 0

    init {
        quoteList = readQuoteFromAssets()
    }

    private fun readQuoteFromAssets(): Array<Quote> {
        val inputStream = context.assets.open("quotes.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, Array<Quote>::class.java)
    }

    fun getQuote() = quoteList[index]

    fun nextQuote() = quoteList[if (index == quoteList.size-1) index else ++index]

    fun previousQuote() = quoteList[if (index == 0) 0 else --index]
}