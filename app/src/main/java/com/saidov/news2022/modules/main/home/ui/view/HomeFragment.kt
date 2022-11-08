package com.saidov.news2022.modules.main.home.ui.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.saidov.news2022.R
import com.saidov.news2022.core.callback.OnSearchListener
import com.saidov.news2022.core.fragment.BaseFragment
import com.saidov.news2022.modules.main.home.ui.adapter.PagerAdapter
import com.saidov.news2022.modules.main.home.ui.model.TabLayoutModel
import com.saidov.news2022.modules.main.ui.vm.SharedViewModel
import kotlin.properties.Delegates


class HomeFragment : BaseFragment(R.layout.fragment_home), OnSearchListener {

    private var pagerAdapter: PagerAdapter by Delegates.notNull()
    private var tabLayout: TabLayout by Delegates.notNull()
    private var viewPager: ViewPager2 by Delegates.notNull()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        sharedViewModel.setToolbarName(getString(R.string.menu_home))
    }

    private fun initView() {
        tabLayout = findViewByID(R.id.tabLayout)
        viewPager = findViewByID(R.id.viewpager)
    }

    override fun onSearch(query: String) {
        if (isResumed) {
            val pageActive =
                pagerAdapter.getFragment(tabLayout.selectedTabPosition) as OnSearchListener
            pageActive.onSearch(query)
        }
    }

    private fun initData() {
        pagerAdapter = PagerAdapter(this)
        sharedViewModel.settingsCategory.value?.let {
            for (i in it) {
                if (i.isChecked) {
                    sharedViewModel.addToHash(i.category)
                    pagerAdapter.addFragment(
                        TabLayoutModel(
                            ViewPagerFragment.newInstance(
                                i.category, i.country
                            ), i.name
                        )
                    )
                } else {
                    sharedViewModel.removeFromHash(i.category)
                }
            }
        }
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = pagerAdapter.getTableLayoutTitle(position)
        }.attach()
    }

}



