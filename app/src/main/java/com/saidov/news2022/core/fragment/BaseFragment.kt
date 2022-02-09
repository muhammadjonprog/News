package com.saidov.news2022.core.fragment

import android.widget.Toolbar
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.appbar.MaterialToolbar

/**
 * Created by MUHAMMADJON SAIDOV on 06,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
abstract class BaseFragment(@LayoutRes n : Int) : Fragment(n) {

    fun clearMenu(){

    }

    fun setMenu(){

    }

  open fun onSearch(query : String){

    }

//    fun setTitle (title : String) : Toolbar{
//
//    }


//    fun setToolbar(n : Int) : Toolbar{
//
//    }

}