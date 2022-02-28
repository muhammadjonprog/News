package com.saidov.news2022.modules.main.settings.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saidov.news2022.R
import com.saidov.news2022.core.callback.OnToolBarChangedListener
import com.saidov.news2022.core.fragment.BaseFragment
import com.saidov.news2022.modules.main.settings.adapter.SettingsCategoryAdapter
import com.saidov.news2022.modules.main.ui.vm.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SettingsFragment() : BaseFragment(R.layout.fragment_settings) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var settingsAdapter: SettingsCategoryAdapter
    var listener: OnToolBarChangedListener? = null
    private val viewModel: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        listener()
        viewModel()

    }


    private fun viewModel() {
        viewModel.settingsCategory.observe(viewLifecycleOwner, Observer {
            settingsAdapter.setItems(it)
        })
    }

    private fun init(view: View) {
        recyclerView = view.findViewById(R.id.recyclerSettingsCategory)
        settingsAdapter = SettingsCategoryAdapter()
        listener?.setToolbarName("Настройки")
    }

    private fun listener() {
        val manager: LinearLayoutManager = GridLayoutManager(context, 1)
        recyclerView.layoutManager = manager
        recyclerView.adapter = settingsAdapter
    }

    companion object {
        fun newInstance(listener: OnToolBarChangedListener?): SettingsFragment {
            val fragment: SettingsFragment = SettingsFragment()
            fragment.listener = listener
            return fragment
        }
    }
}