package com.saidov.news2022.repository.dbrepository

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import com.saidov.news2022.modules.main.ui.model.Article
import com.saidov.news2022.modules.main.ui.model.Source

/**
 * Created by MUHAMMADJON SAIDOV on 30,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 *
 */
class SqlRepositoryImpl(context: Context) : MySQLiteOpenHelper(context), ISqlRepository {

    override fun saveHistory(article: Article) {
        val sql = "INSERT or replace INTO History (author, title, description, url," +
                " urlToImage, publishedAt, content, source) VALUES ('${article.author}', '${article.title}', " +
                "'${article.description}','${article.url}','${article.urlToImage}'," +
                "'${article.publishedAt}','${article.content}' ,'${article.source?.name}')"
        Execute(sql)
    }

    override fun saveFavorite(article: Article) {
        val sql = "INSERT or replace INTO Favorite (author, title, description, url," +
                " urlToImage, publishedAt, content, source) VALUES ('${article.author}', '${article.title}', " +
                "'${article.description}','${article.url}','${article.urlToImage}'," +
                "'${article.publishedAt}','${article.content}','${article.source?.name}')"
        Execute(sql)
    }

    @SuppressLint("Range")
    override fun getArticleHistory(): ArrayList<Article> {
        val historyList = ArrayList<Article>()
        val source: Source? = null
        val sql = "SELECT * FROM History ORDER BY id DESC"
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
                    historyList.add(
                        Article(
                            id,
                            source,
                            author,
                            title,
                            description,
                            url,
                            urlToImage,
                            publishedAt,
                            content
                        )
                    )
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return historyList
    }

    @SuppressLint("Range")
    override fun getArticleFavorite(): ArrayList<Article> {
        val favoriteList = ArrayList<Article>()
        val source: Source? = null
        val sql = "SELECT * FROM Favorite ORDER BY id DESC"
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
                    favoriteList.add(
                        Article(
                            id,
                            source,
                            author,
                            title,
                            description,
                            url,
                            urlToImage,
                            publishedAt,
                            content
                        )
                    )
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return favoriteList
    }

    override fun deleteHistory(id: Long): Boolean {
        val deleteQuery = "DELETE FROM History WHERE id = $id"
        return ExecuteWithResult(deleteQuery)
    }

    override fun deleteFavorite(id: Long): Boolean {
        val deleteQuery = "DELETE FROM Favorite WHERE id = $id"
        return ExecuteWithResult(deleteQuery)
    }

    @SuppressLint("Range")
    override fun searchByHistory(query: String): ArrayList<Article> {
        val searchList = ArrayList<Article>()
        val source: Source? = null
        val sql = "SELECT * FROM History WHERE title  LIKE  '%$query%'"
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
                    searchList.add(
                        Article(
                            id,
                            source,
                            author,
                            title,
                            description,
                            url,
                            urlToImage,
                            publishedAt,
                            content
                        )
                    )
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return searchList
    }

    @SuppressLint("Range")
    override fun searchByFavorite(query: String): ArrayList<Article> {
        val searchList = ArrayList<Article>()
        val source: Source? = null
        val sql = "SELECT * FROM Favorite WHERE title  LIKE '%$query%'"
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
                    searchList.add(
                        Article(
                            id,
                            source,
                            author,
                            title,
                            description,
                            url,
                            urlToImage,
                            publishedAt,
                            content
                        )
                    )
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return searchList
    }
}