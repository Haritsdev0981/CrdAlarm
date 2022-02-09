package com.harets.smartalarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.harets.smartalarm.data.local.AlarmDB
import com.harets.smartalarm.data.local.AlarmDao
import com.harets.smartalarm.databinding.ActivityRepeatingAlarmBinding
import com.harets.smartalarm.helper.TAG_TIME_PICKER
import com.harets.smartalarm.helper.timeFormatter

class RepeatingAlarmActivity : AppCompatActivity(), TimePickerFrament.TimeDialogListener{

    private var _binding :ActivityRepeatingAlarmBinding? = null
    private val binding get() = _binding as ActivityRepeatingAlarmBinding

    private var alarmDao : AlarmDao? = null

    private var _alarmService: AlarmService? = null
    private val alarmService get() =  _alarmService as AlarmService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRepeatingAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AlarmDB.getDatabase(this)
        alarmDao = db.alarmDao()

        _alarmService = AlarmService()

        initView()
    }

    private fun initView() {
        binding.apply {
            btnSetTimeRepeating.setOnClickListener {
                val timePickerFrament = TimePickerFrament()
                timePickerFrament.show(supportFragmentManager, TAG_TIME_PICKER)
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        binding.tvRepeatingTime.text = timeFormatter(hourOfDay, minute)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}