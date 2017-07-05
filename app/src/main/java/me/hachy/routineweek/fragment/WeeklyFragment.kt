package me.hachy.routineweek.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_weekly.*

import java.util.Calendar
import java.util.TimeZone

import me.hachy.routineweek.R
import me.hachy.routineweek.adapter.WeeklyPagerAdapter


class WeeklyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_weekly, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cal = Calendar.getInstance(TimeZone.getDefault())
        val day = cal.get(Calendar.DAY_OF_WEEK)

        viewPager.adapter = WeeklyPagerAdapter(fragmentManager, activity)
        tabLayout.setupWithViewPager(viewPager)

        when (day) {
            Calendar.MONDAY -> viewPager.currentItem = 0
            Calendar.TUESDAY -> viewPager.currentItem = 1
            Calendar.WEDNESDAY -> viewPager.currentItem = 2
            Calendar.THURSDAY -> viewPager.currentItem = 3
            Calendar.FRIDAY -> viewPager.currentItem = 4
            Calendar.SATURDAY -> viewPager.currentItem = 5
            Calendar.SUNDAY -> viewPager.currentItem = 6
        }
    }

    companion object {

        fun newInstance(): WeeklyFragment {
            return WeeklyFragment()
        }
    }

}// Required empty public constructor
