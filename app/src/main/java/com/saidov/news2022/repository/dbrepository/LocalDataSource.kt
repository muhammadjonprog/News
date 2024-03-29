package com.saidov.news2022.repository.dbrepository

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import com.saidov.news2022.modules.main.ui.model.ArticleModel
import com.saidov.news2022.modules.main.ui.model.SourceModel

/**
 * Created by MUHAMMADJON SAIDOV on 30,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
interface LocalDataSource {

    fun saveHistory(articleModel: ArticleModel)

    fun saveFavorite(articleModel: ArticleModel)

    fun getArticleHistory(): ArrayList<ArticleModel>

    fun getArticleFavorite(): ArrayList<ArticleModel>

    fun deleteHistory(id: Long): Boolean

    fun deleteFavorite(id: Long): Boolean

    fun searchByHistory(query: String): ArrayList<ArticleModel>

    fun searchByFavorite(query: String): ArrayList<ArticleModel>

    class Base(context: Context) : MySQLiteOpenHelper(context), LocalDataSource {

        override fun saveHistory(articleModel: ArticleModel) {
            val sql =
                "INSERT or replace INTO History (author, title, description, url," + " urlToImage, publishedAt, content, source) VALUES ('${articleModel.author}', '${articleModel.title}', " + "'${articleModel.description}','${articleModel.url}','${articleModel.urlToImage}'," + "'${articleModel.publishedAt}','${articleModel.content}' ,'${articleModel.sourceModel?.name}')"
            Execute(sql)
        }

        override fun saveFavorite(articleModel: ArticleModel) {
            val sql =
                "INSERT or replace INTO Favorite (author, title, description, url," + " urlToImage, publishedAt, content, source) VALUES ('${articleModel.author}', '${articleModel.title}', " + "'${articleModel.description}','${articleModel.url}','${articleModel.urlToImage}'," + "'${articleModel.publishedAt}','${articleModel.content}','${articleModel.sourceModel?.name}')"
            Execute(sql)
        }

        @SuppressLint("Range")
        override fun getArticleHistory(): ArrayList<ArticleModel> {
            val historyList = ArrayList<ArticleModel>()
            val sourceModel: SourceModel? = null
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
                        sourceModel?.name = mCursor.getString(mCursor.getColumnIndex("source"))
                        historyList.add(
                            ArticleModel(
                                id,
                                sourceModel,
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
        override fun getArticleFavorite(): ArrayList<ArticleModel> {
            val favoriteList = ArrayList<ArticleModel>()
            val sourceModel: SourceModel? = null
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
                        sourceModel?.name = mCursor.getString(mCursor.getColumnIndex("source"))
                        favoriteList.add(
                            ArticleModel(
                                id,
                                sourceModel,
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
        override fun searchByHistory(query: String): ArrayList<ArticleModel> {
            val searchList = ArrayList<ArticleModel>()
            val sourceModel: SourceModel? = null
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
                        sourceModel?.name = mCursor.getString(mCursor.getColumnIndex("source"))
                        searchList.add(
                            ArticleModel(
                                id,
                                sourceModel,
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
        override fun searchByFavorite(query: String): ArrayList<ArticleModel> {
            val searchList = ArrayList<ArticleModel>()
            val sourceModel: SourceModel? = null
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
                        sourceModel?.name = mCursor.getString(mCursor.getColumnIndex("source"))
                        searchList.add(
                            ArticleModel(
                                id,
                                sourceModel,
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
}