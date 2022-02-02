package com.saidov.news2022.modules.main.ui.callback

/**
 * Created by MUHAMMADJON SAIDOV on 30,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 *
 */
interface OnToolbarListener {

    fun clearMenu()

    fun setDisplayHomeEnabled(boolean: Boolean)

    fun setMenu()

    fun setToolbarTitle(title : String)
}