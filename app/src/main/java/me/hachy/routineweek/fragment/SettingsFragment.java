package me.hachy.routineweek.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.hachy.routineweek.R;
import me.hachy.routineweek.activity.LicenseActivity;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView license = (TextView) view.findViewById(R.id.open_source_license);
        license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LicenseActivity.createIntent(getContext()));
            }
        });

        return view;
    }

}
