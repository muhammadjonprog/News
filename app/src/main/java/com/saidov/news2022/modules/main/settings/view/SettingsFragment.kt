package com.saidov.news2022.modules.main.settings.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saidov.news2022.R
import com.saidov.news2022.core.fragment.BaseFragment
import com.saidov.news2022.modules.main.settings.adapter.SettingsCategoryAdapter
import com.saidov.news2022.modules.main.ui.vm.SharedViewModel
import kotlin.properties.Delegates


class SettingsFragment() : BaseFragment(R.layout.fragment_settings) {

    private var recyclerView: RecyclerView by Delegates.notNull()
    private var settingsAdapter: SettingsCategoryAdapter by Delegates.notNull()
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listener()
        viewModel()
    }

    private fun viewModel() {
        viewModel.settingsCategory.observe(viewLifecycleOwner) {
            settingsAdapter.setItems(it)
        }
    }

    private fun init() {
        recyclerView = findViewByID(R.id.recyclerSettingsCategory)
        settingsAdapter = SettingsCategoryAdapter()
        viewModel.setToolbarName(getString(R.string.menu_settings))
    }

    private fun listener() {
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.adapter = settingsAdapter
    }

}