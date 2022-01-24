package com.saidov.news2022.repository

import com.saidov.news2022.model.Article
import com.saidov.news2022.network.ApiClient

/**
 * Created by MUHAMMADJON SAIDOV on 20,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 *
 */
 class NewsRepository(
  //  val db: ArticleDatabase //parameter
) {

    /*
    function that directly queries our api for the breaking news
     */
//    suspend fun getBreakingNews(countryCode:String, pageNumber:Int)=
//        RetroInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun getBreakingNews()=
        ApiClient.api.getBreakingNews()
    /*
    function that query our api for searched news
     */
    suspend fun searchNews(searchQuery: String, pageNumber: Int)=
        ApiClient.api.searchForNews(searchQuery, pageNumber)

    /*
    function to insert article to db
     */
//    suspend fun upsert(article: Article)=
//        db.getArticleDao().upsert(article)

    /*
    function to get saved news from db
     */
//    fun getSavedNews()=
//      db.getArticleDao().getAllArticles()

    /*
    function to delete articles from db
     */
//    suspend fun deleteArticle(article: Article)=
//      db.getArticleDao().deleteArticle(article)
}