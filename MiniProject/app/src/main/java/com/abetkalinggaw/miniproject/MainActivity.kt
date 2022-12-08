package com.abetkalinggaw.miniproject

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var tvDatePicker: TextView
    private lateinit var btnDatePicker: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSearch = findViewById<Button>(R.id.btn_Search)
        btnSearch.setOnClickListener {
            val intent = Intent(this, KelasTiketActivity::class.java)

            startActivity(intent)
            

            tvDatePicker = findViewById(R.id.tvDate)
            btnDatePicker = findViewById(R.id.btnDatePicker)
            val myCalendar = Calendar.getInstance()
            val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd-mm-yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.UK)
                tvDatePicker.setText(sdf.format(myCalendar.time))
            }
            btnDatePicker.setOnClickListener {
                DatePickerDialog(
                    this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }
}


