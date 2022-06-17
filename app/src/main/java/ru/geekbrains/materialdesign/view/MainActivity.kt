package ru.geekbrains.materialdesign.view

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import ru.geekbrains.materialdesign.R
import ru.geekbrains.materialdesign.view.picture.PictureOfTheDayFragment

const val ThemeRed = 1
const val ThemeBlue = 2
const val ThemeGreen = 3

const val KEY_SP = "sp"
const val KEY_SP_CURRENT_THEME = "current_theme"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getCurrentTheme() > 0) {
            setTheme(getRealStyle(getCurrentTheme()))
        }
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // TODO не применяет красную тему
        Log.i("@2 THEME", getCurrentTheme().toString())
    }

    private fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_SP_CURRENT_THEME, -1)
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeRed -> R.style.MyRedStyle
            ThemeBlue -> R.style.MyBlueStyle
            ThemeGreen -> R.style.MyGreenStyle
            else -> 0
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setTheme(getRealStyle(getCurrentTheme()))
    }

}