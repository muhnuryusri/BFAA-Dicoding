package com.bangkit.githubuser

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bangkit.githubuser.alarm.AlarmReceiver
import com.bangkit.githubuser.databinding.ActivityNotificationSettingsBinding

class NotificationSettingsActivity : AppCompatActivity() {
    companion object {
        const val PREFS_NAME = "SettingPref"
        private const val DAILY = "daily"
    }

    private lateinit var binding: ActivityNotificationSettingsBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.notification_settings)

        alarmReceiver = AlarmReceiver()
        mSharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        setSwitch()
        binding.switchDaily.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setDailyReminder(
                    this,
                    AlarmReceiver.TYPE_DAILY,
                    getString(R.string.daily_reminder)
                )
            } else {
                alarmReceiver.cancelAlarm(this)
            }
            saveChange(isChecked)
        }
    }

    private fun setSwitch() {
        binding.switchDaily.isChecked = mSharedPreferences.getBoolean(DAILY, false)
    }

    private fun saveChange(value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(DAILY, value)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}