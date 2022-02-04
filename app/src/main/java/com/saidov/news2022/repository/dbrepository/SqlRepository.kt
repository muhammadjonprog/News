package com.saidov.news2022.repository.dbrepository

import com.saidov.news2022.modules.main.ui.model.Article

/**
 * Created by MUHAMMADJON SAIDOV on 30,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
interface SqlRepository  {

    fun saveHistory(article : Article)

    fun saveFavorite(article : Article)

    fun getArticleHistory() : ArrayList<Article>

    fun getArticleFavorite() : ArrayList<Article>

    fun deleteHistory(id: Long) : Boolean

    fun deleteFavorite(id : Long) : Boolean
}