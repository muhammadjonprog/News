package com.saidov.news2022.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import com.saidov.news2022.model.Article
import com.saidov.news2022.model.Source
import com.saidov.news2022.network.ApiClient

/**
 * Created by MUHAMMADJON SAIDOV on 20,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
 class NewsRepository(context : Context) : NewsDB(context) {

    fun insertNewsHistory(article: Article){
        val sql = "INSERT or replace INTO History (author, title, description, url," +
                " urlToImage, publishedAt, content, source) VALUES ('${article.author}', '${article.title}', " +
                "'${article.description}','${article.url}','${article.urlToImage}'," +
                "'${article.publishedAt}','${article.content}' ,'${article.source?.name}')"
        Execute(sql)
    }

    fun insertNewsFavorites(article: Article){
        val sql = "INSERT or replace INTO Favorite (author, title, description, url," +
                " urlToImage, publishedAt, content, source) VALUES ('${article.author}', '${article.title}', " +
                "'${article.description}','${article.url}','${article.urlToImage}'," +
                "'${article.publishedAt}','${article.content}','${article.source?.name}')"
              Execute(sql)
    }

    @SuppressLint("Range")
    fun getArticleHistory() : ArrayList<Article>{
        val historyList = ArrayList<Article>()
        val source : Source? = null
        val sql = "SELECT * from History"
        val mCursor: Cursor? = Query(sql)
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    val id = mCursor.getLong(mCursor.getColumnIndex("id"))
                    val author = mCursor.getString(mCursor.getColumnIndex("author"))
                    val title = mCursor.getString(mCursor.getColumnIndex("title"))
                    val description = mCursor.getString(mCursor.getColumnIndex("description"))
                    val url = mCursor.getString(mCursor.getColumnIndex("url"))
                    val urlToImage = mCursor.getString(mCursor.getColumnIndex("urlToImage"))
                    val publishedAt = mCursor.getString(mCursor.getColumnIndex("publishedAt"))
                    val content = mCursor.getString(mCursor.getColumnIndex("content"))
                    source?.name = mCursor.getString(mCursor.getColumnIndex("source"))
                   historyList.add(Article(id,source,author,title,description,url,urlToImage,publishedAt,content))
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return historyList
    }

    @SuppressLint("Range")
    fun deleteArticleHistory(id : Long): Boolean{
        val deleteQuery = "DELETE FROM History WHERE id = $id"
        return ExecuteWithResult(deleteQuery)
    }

    @SuppressLint("Range")
    fun deleteArticleFavorite(id : Long): Boolean{
        val deleteQuery = "DELETE FROM Favorite WHERE id = $id"
        return ExecuteWithResult(deleteQuery)
    }

    @SuppressLint("Range")
    fun getArticleFavorite(): ArrayList<Article>{
        val favoriteList = ArrayList<Article>()
        val source : Source? = null
        val sql = "SELECT * from Favorite"
        val mCursor: Cursor? = Query(sql)
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    val id = mCursor.getLong(mCursor.getColumnIndex("id"))
                    val author = mCursor.getString(mCursor.getColumnIndex("author"))
                    val title = mCursor.getString(mCursor.getColumnIndex("title"))
                    val description = mCursor.getString(mCursor.getColumnIndex("description"))
                    val url = mCursor.getString(mCursor.getColumnIndex("url"))
                    val urlToImage = mCursor.getString(mCursor.getColumnIndex("urlToImage"))
                    val publishedAt = mCursor.getString(mCursor.getColumnIndex("publishedAt"))
                    val content = mCursor.getString(mCursor.getColumnIndex("content"))
                    source?.name = mCursor.getString(mCursor.getColumnIndex("source"))
                    favoriteList.add(Article(id,source,author,title,description,url,urlToImage,publishedAt,content))
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return favoriteList
    }

    fun closeDB() {
        close()
    }

    fun openDB() {
        open()
    }

    suspend fun getBreakingNews() =
        ApiClient.api.getBreakingNews()


    suspend fun searchNews(searchQuery: String, pageNumber: Int)=
        ApiClient.api.searchForNews(searchQuery, pageNumber)

}