package com.saidov.news2022.modules.main.home.ui.view

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.saidov.news2022.R
import com.saidov.news2022.core.fragment.BaseFragmentWithSharedViewModel
import com.saidov.news2022.modules.main.home.ui.adapter.PagerAdapter
import com.saidov.news2022.modules.main.home.ui.model.TabLayoutModel
import com.saidov.news2022.modules.main.ui.vm.MainViewModel

class NewsFragment : BaseFragmentWithSharedViewModel<MainViewModel>(MainViewModel::class.java,R.layout.fragment_news){

    lateinit var pagerAdapter: PagerAdapter
    lateinit var tabLayout : TabLayout
    lateinit var viewPager : ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(view)
    }

    private fun initData(view: View) {
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewpager)
        pagerAdapter = PagerAdapter(this)
        viewmodel.settingsCategory.value?.let{
            for (i in it){
                if (i.isChecked){
                    //add hash code
                    viewmodel.addToHash(i.name)
                    pagerAdapter.addFragment(TabLayoutModel(CategoryNewsFragment(i.category,i.country),i.name))
                }else{
                    //remove hash code
                    viewmodel.removeFromHash(i.name)
                }
            }
        }
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout,viewPager) {tab, position ->
            tab.text = pagerAdapter.getTableLayoutTitle(position)
        }.attach()
    }
}


