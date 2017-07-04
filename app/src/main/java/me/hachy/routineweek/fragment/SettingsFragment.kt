package me.hachy.routineweek.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_settings.*

import me.hachy.routineweek.R
import me.hachy.routineweek.activity.LicenseActivity

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        license.setOnClickListener { startActivity(LicenseActivity.createIntent(context)) }
    }

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

}// Required empty public constructor
