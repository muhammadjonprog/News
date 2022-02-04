package com.saidov.news2022.modules.main.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.saidov.news2022.R


class SettingsFragment : Fragment() {

    lateinit var listCheck : ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val checkSport = view.findViewById<CheckBox>(R.id.checkSport)
        val checkNature = view.findViewById<CheckBox>(R.id.checkNature)
        val checkPolitics = view.findViewById<CheckBox>(R.id.checkPolitics)
        val checkScience = view.findViewById<CheckBox>(R.id.checkScience)
        val checkBusiness = view.findViewById<CheckBox>(R.id.checkBusiness)



        return view
    }

}