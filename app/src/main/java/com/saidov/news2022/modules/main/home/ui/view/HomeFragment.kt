package com.saidov.news2022.modules.main.home.ui.view

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.saidov.news2022.R
import com.saidov.news2022.core.callback.OnToolBarChangedListener
import com.saidov.news2022.core.fragment.BaseFragmentWithSharedViewModel
import com.saidov.news2022.modules.main.home.ui.adapter.PagerAdapter
import com.saidov.news2022.modules.main.home.ui.model.TabLayoutModel
import com.saidov.news2022.modules.main.ui.vm.MainViewModel

class HomeFragment :
    BaseFragmentWithSharedViewModel<MainViewModel>(
        MainViewModel::class.java,
        R.layout.fragment_home
    ) {

    lateinit var pagerAdapter: PagerAdapter
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    var listener: OnToolBarChangedListener? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(view)
        listener?.setToolbarName("Главная")
    }

    override fun onSearch(query: String) {
        if (isResumed) {
            pagerAdapter.getFragment(tabLayout.selectedTabPosition).onSearch(query = query)
        }
    }

    private fun initData(view: View) {
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewpager)
        pagerAdapter = PagerAdapter(this)
        viewModel.settingsCategory.value?.let {
            for (i in it) {
                if (i.isChecked) {
                    viewModel.addToHash(i.category)
                    pagerAdapter.addFragment(TabLayoutModel(ViewPagerFragment.newInstance(i.category, i.country), i.name))
                } else {
                    viewModel.removeFromHash(i.category)
                }
            }
        }
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = pagerAdapter.getTableLayoutTitle(position)
        }.attach()
    }
    companion object {
        fun newInstance(listener: OnToolBarChangedListener?): HomeFragment {
            val fragment: HomeFragment = HomeFragment()
            fragment.listener = listener
            return fragment
        }
    }
}



