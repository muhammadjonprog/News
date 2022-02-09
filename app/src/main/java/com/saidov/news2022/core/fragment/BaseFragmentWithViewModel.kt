package com.saidov.news2022.core.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.saidov.news2022.core.viewmodel.BaseViewModel

/**
 * Created by MUHAMMADJON SAIDOV on 08,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

abstract class BaseFragmentWithViewModel  <T : BaseViewModel> (clazz:Class<T>,@LayoutRes n : Int)  : BaseFragment(n){
    protected val viewmodel by lazy{
        ViewModelProvider(requireActivity())[clazz]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}