package me.hachy.routineweek.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import me.hachy.routineweek.R;
import me.hachy.routineweek.fragment.WeeklyListFragment;

public class WeeklyPagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGE_COUNT = 7;
    private Context context;

    public WeeklyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String[] TITLES = {
                context.getResources().getString(R.string.day_1),
                context.getResources().getString(R.string.day_2),
                context.getResources().getString(R.string.day_3),
                context.getResources().getString(R.string.day_4),
                context.getResources().getString(R.string.day_5),
                context.getResources().getString(R.string.day_6),
                context.getResources().getString(R.string.day_7),
        };
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        return WeeklyListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}


