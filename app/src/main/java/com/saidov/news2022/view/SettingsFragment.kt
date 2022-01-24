package com.saidov.news2022.view

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.saidov.news2022.R


class SettingsFragment : Fragment() {

    lateinit var sharedTheme : SharedPreferences
    lateinit var radioGroup : RadioGroup
    lateinit var switch : SwitchCompat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        radioGroup = view.findViewById(R.id.radioGroup)
        switch = view.findViewById(R.id.switchTheme)
        sharedTheme = requireContext().getSharedPreferences("mode", 0)
        val bool: Boolean = sharedTheme.getBoolean("night_mode", true)

        if (bool) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            switch.isChecked = true

            switch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switch.isChecked = true
                    val editor: SharedPreferences.Editor = sharedTheme.edit()
                    editor.putBoolean("night_mode", true)
                    editor.apply()
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switch.isChecked = false
                    val editor: SharedPreferences.Editor = sharedTheme.edit()
                    editor.putBoolean("night_mode", false)
                    editor.apply()
                }
            })

        }
        return view
    }

}