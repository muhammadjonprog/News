package com.saidov.news2022.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import com.saidov.news2022.db.NewsDB
import com.saidov.news2022.model.Article
import com.saidov.news2022.network.ApiClient

/**
 * Created by MUHAMMADJON SAIDOV on 20,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
 class NewsRepository(context : Context) : NewsDB(context) {


    fun insertNewsHistory(article: Article){

        val sql = "INSERT or replace INTO History (author, title, description, url," +
                " urlToImage, publishedAt, content) VALUES ('${article.author}', '${article.title}', " +
                "'${article.description}','${article.url}','${article.urlToImage}'," +
                "'${article.publishedAt}','${article.content}')"
       // val sql ="INSERT OR REPLACE INTO  table_words SET favorite=$favorite WHERE id=$word_id"
        Execute(sql)
    }

    fun insertNewsFavorites(article: Article){

        val sql = "INSERT or replace INTO Favorite (author, title, description, url," +
                " urlToImage, publishedAt, content) VALUES ('${article.author}', '${article.title}', " +
                "'${article.description}','${article.url}','${article.urlToImage}'," +
                "'${article.publishedAt}','${article.content}')"
        // val sql ="INSERT OR REPLACE INTO  table_words SET favorite=$favorite WHERE id=$word_id"
        Execute(sql)
    }



    @SuppressLint("Range")
    fun getArticleHistory(): ArrayList<Article>{
        val historyList = ArrayList<Article>()
        val sql = "SELECT * from History"
        val mCursor: Cursor? = Query(sql)
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    val id = mCursor.getInt(mCursor.getColumnIndex("id"))
                    val author = mCursor.getString(mCursor.getColumnIndex("author"))
                    val title = mCursor.getString(mCursor.getColumnIndex("title"))
                    val description = mCursor.getString(mCursor.getColumnIndex("description"))
                    val url = mCursor.getString(mCursor.getColumnIndex("url"))
                    val urlToImage = mCursor.getString(mCursor.getColumnIndex("urlToImage"))
                    val publishedAt = mCursor.getString(mCursor.getColumnIndex("publishedAt"))
                    val content = mCursor.getString(mCursor.getColumnIndex("content"))
                    val source = mCursor.getString(mCursor.getColumnIndex("source"))
                    //historyList.add(Article(id,source,author,title,description,url,urlToImage,publishedAt,content))
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return historyList
    }


    @SuppressLint("Range")
    fun getFavoriteArticle(): ArrayList<Article> {
        val arrayList = ArrayList<Article>()
        val sql = "SELECT * FROM table_words WHERE favorite = 1"
        val mCursor = Query(sql)
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    val id = mCursor.getInt(mCursor.getColumnIndex("id"))
                    val theme_tj = mCursor.getString(mCursor.getColumnIndex("tj"))
                    val theme_rus = mCursor.getString(mCursor.getColumnIndex("rus"))
                    val isFavorite = mCursor.getInt(mCursor.getColumnIndex("favorite")) == 1
                   // arrayList.add(ThemeModel(id, theme_tj, theme_rus,isFavorite))
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return arrayList
    }


    fun closeDB() {
        close()
    }

    fun openDB() {
        open()
    }


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