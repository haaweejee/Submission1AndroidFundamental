package id.haweje.submission1_githubuser.ui.settings

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import id.haweje.submission1_githubuser.R
import id.haweje.submission1_githubuser.alarm.AlarmReceiver
import id.haweje.submission1_githubuser.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    companion object{
        const val PREFS_NAME = "preferenceName"
        const val ALARM_SET = "alarmSet"
    }

    private lateinit var binding : ActivitySettingsBinding
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            customView = actionBarsetBold()
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        binding.btnLanguage.setOnClickListener {
            intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }

        binding.toogleReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                val message = getString(R.string.lets_search_user)
                alarmReceiver.setDefaultAlarm(this, message)
            }else{
                alarmReceiver.cancelAlarm(this)
            }
            setAlarmPreference()
        }
        alarmReceiver = AlarmReceiver()
        getAlarmPrefence()
    }



    private fun setAlarmPreference(){
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putBoolean(ALARM_SET, binding.toogleReminder.isChecked)
        }.apply()
    }

    private fun getAlarmPrefence(){
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val saveBoolean = sharedPreferences.getBoolean(ALARM_SET, false)
        binding.toogleReminder.isChecked = saveBoolean
    }

    private fun actionBarsetBold() : TextView {
        return TextView(this).apply {
            text = getString(R.string.settings)

            val params = ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            layoutParams = params

            params.gravity = Gravity.START

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setTextAppearance(
                    android.R.style.TextAppearance_Material_Widget_ActionBar_Title
                )
            } else {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 17F)
                setTypeface(null, Typeface.BOLD)
            }

            setTextColor(Color.WHITE)

            typeface = Typeface.SERIF


            setTypeface(typeface, Typeface.BOLD)
        }
    }

    override fun onSupportNavigateUp():Boolean{
        onBackPressed()
        return true
    }
}