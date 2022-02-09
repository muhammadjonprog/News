package com.saidov.news2022.core.fragment

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.saidov.news2022.core.viewmodel.BaseViewModel

/**
 * Created by MUHAMMADJON SAIDOV on 08,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

abstract class BaseFragmentWithSharedViewModel<T:BaseViewModel>(clazz:Class<T>,@LayoutRes n: Int) : BaseFragment(n) {
    protected val viewmodel by lazy{
        ViewModelProvider(requireActivity())[clazz]
    }
}