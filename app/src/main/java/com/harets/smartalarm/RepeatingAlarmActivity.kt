package com.harets.smartalarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.harets.smartalarm.databinding.ActivityRepeatingAlarmBinding
import com.harets.smartalarm.helper.TAG_TIME_PICKER
import com.harets.smartalarm.helper.timeFormatter

class RepeatingAlarmActivity : AppCompatActivity(), TimePickerFrament.TimeDialogListener{

    private var _binding :ActivityRepeatingAlarmBinding? = null
    private val binding get() = _binding as ActivityRepeatingAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRepeatingAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

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