package ru.geekbrains.materialdesign.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.materialdesign.R
import ru.geekbrains.materialdesign.view.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()
    }
}