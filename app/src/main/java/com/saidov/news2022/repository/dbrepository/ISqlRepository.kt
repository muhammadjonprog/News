package com.saidov.news2022.repository.dbrepository

import com.saidov.news2022.modules.main.ui.model.ArticleModel

/**
 * Created by MUHAMMADJON SAIDOV on 30,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
interface ISqlRepository  {

    fun saveHistory(articleModel : ArticleModel)

    fun saveFavorite(articleModel : ArticleModel)

    fun getArticleHistory() : ArrayList<ArticleModel>

    fun getArticleFavorite() : ArrayList<ArticleModel>

    fun deleteHistory(id: Long) : Boolean

    fun deleteFavorite(id : Long) : Boolean

    fun searchByHistory(query : String) : ArrayList<ArticleModel>

    fun searchByFavorite(query : String) : ArrayList<ArticleModel>
}