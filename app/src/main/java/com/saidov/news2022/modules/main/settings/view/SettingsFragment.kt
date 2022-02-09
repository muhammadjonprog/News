package com.saidov.news2022.modules.main.settings.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saidov.news2022.R
import com.saidov.news2022.core.fragment.BaseFragmentWithSharedViewModel
import com.saidov.news2022.modules.main.settings.adapter.SettingsCategoryRecyclerAdapter
import com.saidov.news2022.modules.main.ui.callback.OnToolbarListener
import com.saidov.news2022.modules.main.ui.vm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SettingsFragment() :
    BaseFragmentWithSharedViewModel<MainViewModel>(MainViewModel::class.java,R.layout.fragment_settings) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var settingsAdapter: SettingsCategoryRecyclerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        listener()
        viewModel()
    }

    private fun viewModel() {
        viewmodel.settingsCategory.observe(viewLifecycleOwner, Observer {
            settingsAdapter.setItems(it)
        })
    }

    private fun init(view: View) {
        recyclerView = view.findViewById(R.id.recyclerSettingsCategory)
        settingsAdapter = SettingsCategoryRecyclerAdapter()
    }

    private fun listener() {
        val manager: LinearLayoutManager = GridLayoutManager(context, 1)
        recyclerView.layoutManager = manager
        recyclerView.adapter = settingsAdapter
    }

//  companion object{
//      fun newInstance(): SettingsFragment{
//          val args = Bundle()
//
//          val fragment = SettingsFragment()
//          fragment.onToolbarListener
//          return fragment
//      }
//  }

}