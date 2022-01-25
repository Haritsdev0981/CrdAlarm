package com.harets.smartalarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.harets.smartalarm.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        binding.apply {
            cvSetOneTimeAlarm.setOnClickListener {
                startActivity(Intent(applicationContext, OneTimeAlarmActivity::class.java))
            }
            cvSetRepeatingAlarm.setOnClickListener {
                startActivity(Intent(this@MainActivity, RepeatingAlarmActivity::class.java))
            }


//            getTimeToday()
        }
    }

    private fun getTimeToday(){ //dd/M/yyyy hh:mm:ss
        val calendar = Calendar.getInstance() //getInstance untuk meng inisialisasi
        val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()) // HH:MM = hour.Minute
        val time = formattedTime.format(calendar.time)

//        https://stackoverflow.com/questions/47006254/how-to-get-current-local-date-and-time-in-kotlin

        binding.tvTimeToday.text = time
    }
}