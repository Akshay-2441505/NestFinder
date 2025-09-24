package com.example.finalminiproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

class EmiCalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emi_calculator)

        val toolbar: Toolbar = findViewById(R.id.emi_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "EMI Calculator"

        val amountEditText: EditText = findViewById(R.id.edit_text_amount)
        val rateEditText: EditText = findViewById(R.id.edit_text_rate)
        val yearsEditText: EditText = findViewById(R.id.edit_text_years)
        val calculateButton: Button = findViewById(R.id.button_calculate)
        val emiResultTextView: TextView = findViewById(R.id.text_view_result_emi)
        val interestResultTextView: TextView = findViewById(R.id.text_view_result_interest)
        val totalResultTextView: TextView = findViewById(R.id.text_view_result_total)

        calculateButton.setOnClickListener {
            val amountStr = amountEditText.text.toString()
            val rateStr = rateEditText.text.toString()
            val yearsStr = yearsEditText.text.toString()

            if (amountStr.isEmpty() || rateStr.isEmpty() || yearsStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val principal = amountStr.toDouble()
            val annualRate = rateStr.toDouble()
            val years = yearsStr.toInt()

            // EMI Calculation Logic
            val monthlyRate = annualRate / 12 / 100
            val months = years * 12
            val emi = (principal * monthlyRate * (1 + monthlyRate).pow(months)) / ((1 + monthlyRate).pow(months) - 1)

            val totalPayment = emi * months
            val totalInterest = totalPayment - principal

            // Format results for display
            val indianLocale = Locale("en", "IN")
            val currencyFormatter = NumberFormat.getCurrencyInstance(indianLocale).apply {
                maximumFractionDigits = 0
            }

            emiResultTextView.text = "Monthly EMI: ${currencyFormatter.format(emi)}"
            interestResultTextView.text = "Total Interest: ${currencyFormatter.format(totalInterest)}"
            totalResultTextView.text = "Total Payment: ${currencyFormatter.format(totalPayment)}"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}