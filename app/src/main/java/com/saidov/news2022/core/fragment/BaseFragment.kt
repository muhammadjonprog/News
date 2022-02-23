package com.saidov.news2022.core.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar


/**
 * Created by MUHAMMADJON SAIDOV on 06,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
abstract class BaseFragment(@LayoutRes n: Int) : Fragment(n){

    open fun onSearch(query: String) {}




















//    class Toolbar() {
//         lateinit var toolbar: MaterialToolbar
//         lateinit var baseFragment: BaseFragment
//
//
//        fun clearMenu() {
//            val materialToolbar = toolbar
//            val menu = materialToolbar.menu
//            menu.clear()
//        }

//        fun setMenu(){
//            val materialToolbar = toolbar
//            val menu = materialToolbar.menu
//            baseFragment.onCreateOptionsMenu(menu, baseFragment.requireActivity().menuInflater)
//        }
//
//        fun setDisplayHomeEnable(boolean: Boolean): MaterialToolbar {
//            val fragmentActivity: FragmentActivity? = baseFragment.activity
//            val actionBar: ActionBar? = fragmentActivity?.actionBar
//            actionBar?.setDisplayHomeAsUpEnabled(boolean)
//            return toolbar
//        }
//
//        fun setTitle(title: String): MaterialToolbar {
//            val fragmentActivity: FragmentActivity = baseFragment.requireActivity()
//            val actionBar: ActionBar? = fragmentActivity.actionBar
//            actionBar?.title = title
//            return toolbar
//
//        }
//
//        fun setToolbar(@LayoutRes n: Int): Toolbar {
//            val toolbar = baseFragment.requireView().findViewById<View>(n)
//            baseFragment.setHasOptionsMenu(true)
//            val fragmentActivity = activity
//            return TODO()
//        }
//   }


}




