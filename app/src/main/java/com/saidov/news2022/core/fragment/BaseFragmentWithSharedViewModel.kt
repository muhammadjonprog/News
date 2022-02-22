package com.saidov.news2022.core.fragment

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.saidov.news2022.core.viewmodel.BaseViewModel
import com.saidov.news2022.modules.main.ui.view.MainActivity

/**
 * Created by MUHAMMADJON SAIDOV on 08,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
//TODO: Махсус барои sharedViewModel BaseFragmentWithSharedViewModel сохтан нодуруст. sharedViewModel дарони фрагментба эълон шудан гириад хам мешавад
abstract class BaseFragmentWithSharedViewModel<T:BaseViewModel>(clazz:Class<T>,@LayoutRes n: Int)
    : BaseFragment(n) {
    protected val viewModel by lazy{
        ViewModelProvider(requireActivity())[clazz]
    }
}