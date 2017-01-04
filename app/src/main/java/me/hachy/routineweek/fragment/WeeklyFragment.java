package me.hachy.routineweek.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.TimeZone;

import me.hachy.routineweek.R;
import me.hachy.routineweek.adapter.WeeklyPagerAdapter;


public class WeeklyFragment extends Fragment {

    public WeeklyFragment() {
        // Required empty public constructor
    }

    public static WeeklyFragment newInstance() {
        return new WeeklyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewpager = (ViewPager) view.findViewById(R.id.view_pager);

        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        int day = cal.get(Calendar.DAY_OF_WEEK);

        viewpager.setAdapter(new WeeklyPagerAdapter(getFragmentManager(), getActivity()));
        tabLayout.setupWithViewPager(viewpager);

        if (day == Calendar.MONDAY) {
            viewpager.setCurrentItem(0);
        } else if (day == Calendar.TUESDAY) {
            viewpager.setCurrentItem(1);
        } else if (day == Calendar.WEDNESDAY) {
            viewpager.setCurrentItem(2);
        } else if (day == Calendar.THURSDAY) {
            viewpager.setCurrentItem(3);
        } else if (day == Calendar.FRIDAY) {
            viewpager.setCurrentItem(4);
        } else if (day == Calendar.SATURDAY) {
            viewpager.setCurrentItem(5);
        } else if (day == Calendar.SUNDAY) {
            viewpager.setCurrentItem(6);
        }

        return view;
    }

}
