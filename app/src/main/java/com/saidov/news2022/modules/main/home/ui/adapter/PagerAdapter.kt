package com.saidov.news2022.modules.main.home.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.saidov.news2022.core.fragment.BaseFragment
import com.saidov.news2022.modules.main.home.ui.model.TabLayoutModel

/**
 * Created by MUHAMMADJON SAIDOV on 07,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
class PagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment.childFragmentManager, fragment.lifecycle) {

    private var items: ArrayList<TabLayoutModel> = ArrayList()

    fun addFragment(tabLayoutModel: TabLayoutModel) {
        items.add(tabLayoutModel)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position].fragment
    }


    fun getFragment(position: Int): BaseFragment {
        return items[position].fragment
    }

    fun getTableLayoutTitle(position: Int): String {
        return items[position].title
    }

}