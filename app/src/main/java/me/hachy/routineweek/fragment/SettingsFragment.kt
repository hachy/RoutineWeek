package me.hachy.routineweek.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import me.hachy.routineweek.R
import me.hachy.routineweek.activity.LicenseActivity

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_settings, container, false)

        val license = view.findViewById(R.id.open_source_license) as TextView
        license.setOnClickListener { startActivity(LicenseActivity.createIntent(context)) }

        return view
    }

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

}// Required empty public constructor
