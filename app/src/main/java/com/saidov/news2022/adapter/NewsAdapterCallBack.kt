package com.saidov.news2022.adapter

import android.view.View
import com.saidov.news2022.model.Article

/**
 * Created by MUHAMMADJON SAIDOV on 24,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
interface NewsAdapterCallBack{
    fun itemOnClickListener(item: Article)
    fun itemOnLongClickListener(item: Article, v: View)
}