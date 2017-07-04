package me.hachy.routineweek.adapter


import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

import me.hachy.routineweek.R
import me.hachy.routineweek.fragment.WeeklyListFragment

class WeeklyPagerAdapter(fm: FragmentManager, private val context: Context) : FragmentStatePagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence {
        val TITLES = arrayOf(
                context.resources.getString(R.string.day_1),
                context.resources.getString(R.string.day_2),
                context.resources.getString(R.string.day_3),
                context.resources.getString(R.string.day_4),
                context.resources.getString(R.string.day_5),
                context.resources.getString(R.string.day_6),
                context.resources.getString(R.string.day_7))
        return TITLES[position]
    }

    override fun getItem(position: Int): Fragment {
        return WeeklyListFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

    companion object {

        private val PAGE_COUNT = 7
    }
}


