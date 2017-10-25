package me.hachy.routineweek.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*

import me.hachy.routineweek.R
import me.hachy.routineweek.fragment.DailyFragment
import me.hachy.routineweek.fragment.SettingsFragment
import me.hachy.routineweek.fragment.WeeklyFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager

        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_week -> fragmentManager.beginTransaction().replace(R.id.container, WeeklyFragment.newInstance()).commit()
                R.id.action_daily -> fragmentManager.beginTransaction().replace(R.id.container, DailyFragment.newInstance()).commit()
                R.id.action_settings -> fragmentManager.beginTransaction().replace(R.id.container, SettingsFragment.newInstance()).commit()
            }
            true
        }

        navigation.inflateMenu(R.menu.my_navigation_items)

        fragmentManager.beginTransaction().replace(R.id.container, WeeklyFragment.newInstance()).commit()
    }
}
