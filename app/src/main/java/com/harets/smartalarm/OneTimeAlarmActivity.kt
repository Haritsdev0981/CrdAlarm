package com.harets.smartalarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.harets.smartalarm.data.Alarm
import com.harets.smartalarm.data.local.AlarmDB
import com.harets.smartalarm.data.local.AlarmDao
import com.harets.smartalarm.databinding.ActivityOneTimeAlarmBinding
import com.harets.smartalarm.helper.TAG_TIME_PICKER
import com.harets.smartalarm.helper.timeFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class OneTimeAlarmActivity : AppCompatActivity(), DatePickerFragment.DateDialogListener,
    TimePickerFrament.TimeDialogListener {

    private var _binding: ActivityOneTimeAlarmBinding? = null
    private val binding get() = _binding as ActivityOneTimeAlarmBinding

    private var alarmDao: AlarmDao? = null

    private var _alarmService: AlarmService? = null
    private val alarmService get() = _alarmService as AlarmService

//    private val db by lazy { AlarmDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOneTimeAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AlarmDB.getDatabase(applicationContext)
        alarmDao = db.alarmDao()

        _alarmService = AlarmService()

        initView()
    }

    private fun initView() {
        binding.apply {
            btnSetDateOneTime.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(
                    supportFragmentManager,
                    "DatePickerDialog"
                ) // saat ditekan atau Show(), maka si fragment jam bakal muncul
            }
            btnSetTimeOneTime.setOnClickListener {
                val timePickerFrament = TimePickerFrament()
                timePickerFrament.show(supportFragmentManager, TAG_TIME_PICKER)
            }

            btnAddSetOneTimeAlarm.setOnClickListener {
                val date = tvOnceDate.text.toString()
                val time = tvOnceTime.text.toString()
                val message = etNoteOneTime.text.toString()

                if (date != "Date" && time != "Time") {
                    alarmService.setOneTimeAlarm(applicationContext, 1,date, time, message )
                    CoroutineScope(Dispatchers.IO).launch {
                        alarmDao?.addAlarm(
                            Alarm(
                                0,
                                date,
                                time,
                                message,
                                AlarmService.TYPE_ONE_TIME
                            )
                        )
                        Log.i("AddAlarm", "Success set alarm on $date $time with message ")
                        finish()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Set your Date and your Time.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            btnCancelSetOneTimeAlarm.setOnClickListener {
                finish()
            }
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMoth: Int) {
        val calendar = Calendar.getInstance()
        // mengatur tanggal supaya sama dengan yang sudah dipilih di DatePickerDialog
        calendar.set(year, month, dayOfMoth)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        binding.tvOnceDate.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        binding.tvOnceTime.text = timeFormatter(hourOfDay, minute)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}