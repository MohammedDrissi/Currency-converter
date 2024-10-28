package com.example.currencyconverter

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class secondpage: AppCompatActivity() {
    private lateinit var fromCurrencySpinner: Spinner
    private lateinit var toCurrencySpinner: Spinner
    private lateinit var amountEditText: EditText
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView

    private val currencies = CurrencyData.getAllCurrencies()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondpage)

        fromCurrencySpinner = findViewById(R.id.from_currency_spinner)
        toCurrencySpinner = findViewById(R.id.to_currency_spinner)
        amountEditText = findViewById(R.id.amount_edit_text)
        convertButton = findViewById(R.id.convert_button)
        resultTextView = findViewById(R.id.result_text_view)

        val currencyNames = currencies.map { "${it.name} (${it.code})" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyNames)

        fromCurrencySpinner.adapter = adapter
        toCurrencySpinner.adapter = adapter

        convertButton.setOnClickListener {
            convertCurrency()
        }
    }

    private fun convertCurrency() {
        val fromPosition = fromCurrencySpinner.selectedItemPosition
        val toPosition = toCurrencySpinner.selectedItemPosition

        val fromCurrency = currencies[fromPosition]
        val toCurrency = currencies[toPosition]

        val amountString = amountEditText.text.toString()
        if (amountString.isEmpty()) {
            resultTextView.text = "Please enter the amount"
            return
        }

        val amount = amountString.toDouble()
        val convertedAmount = (amount * fromCurrency.rate) / toCurrency.rate

        resultTextView.text = String.format("Converted Amount : %.2f %s", convertedAmount, toCurrency.code)
    }
}
