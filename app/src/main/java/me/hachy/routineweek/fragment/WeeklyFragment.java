package me.hachy.routineweek.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.hachy.routineweek.R;
import me.hachy.routineweek.activity.AddTaskActivity;
import me.hachy.routineweek.adapter.WeeklyPagerAdapter;


public class WeeklyFragment extends Fragment {

    private ViewPager viewpager;

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
        viewpager = (ViewPager) view.findViewById(R.id.view_pager);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.main_fab);

        viewpager.setAdapter(new WeeklyPagerAdapter(getFragmentManager(), getActivity()));
        tabLayout.setupWithViewPager(viewpager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = viewpager.getCurrentItem();
                startActivity(AddTaskActivity.createIntent(getActivity(), day));
            }
        });

        return view;
    }

}
