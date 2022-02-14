package com.saidov.news2022.core.callback

/**
 * Created by MUHAMMADJON SAIDOV on 13,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
interface OnToolBarChangedListener {

    fun setToolbarName(title:String)

    fun setToolBarBackVisibility(status:Boolean)

    fun clearToolBar()

}