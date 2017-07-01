package me.hachy.routineweek.fragment


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.Calendar
import java.util.TimeZone

import me.hachy.routineweek.R
import me.hachy.routineweek.adapter.WeeklyPagerAdapter


class WeeklyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_weekly, container, false)

        val tabLayout = view.findViewById(R.id.tab_layout) as TabLayout
        val viewpager = view.findViewById(R.id.view_pager) as ViewPager

        val cal = Calendar.getInstance(TimeZone.getDefault())
        val day = cal.get(Calendar.DAY_OF_WEEK)

        viewpager.adapter = WeeklyPagerAdapter(fragmentManager, activity)
        tabLayout.setupWithViewPager(viewpager)

        if (day == Calendar.MONDAY) {
            viewpager.currentItem = 0
        } else if (day == Calendar.TUESDAY) {
            viewpager.currentItem = 1
        } else if (day == Calendar.WEDNESDAY) {
            viewpager.currentItem = 2
        } else if (day == Calendar.THURSDAY) {
            viewpager.currentItem = 3
        } else if (day == Calendar.FRIDAY) {
            viewpager.currentItem = 4
        } else if (day == Calendar.SATURDAY) {
            viewpager.currentItem = 5
        } else if (day == Calendar.SUNDAY) {
            viewpager.currentItem = 6
        }

        return view
    }

    companion object {

        fun newInstance(): WeeklyFragment {
            return WeeklyFragment()
        }
    }

}// Required empty public constructor
