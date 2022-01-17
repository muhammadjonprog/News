package com.saidov.news2022.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saidov.news2022.Adapter.NewsAdapter
import com.saidov.news2022.Common.Common
import com.saidov.news2022.R
import com.saidov.news2022.model.Article
import com.saidov.news2022.model.RetrofitServices
import dmax.dialog.SpotsDialog


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class NewsFragment : Fragment() {
    lateinit var mService: RetrofitServices
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: NewsAdapter
    lateinit var dialog: AlertDialog
    lateinit var recyclerMovieList : RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerMovieList = view.findViewById(R.id.recyclerViewNews)
        mService = Common.retrofitService
        recyclerMovieList.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        recyclerMovieList.layoutManager = layoutManager
        dialog = SpotsDialog.Builder().setCancelable(true).setContext(context).build()
        getAllMovieList()

        return view
    }

    private fun getAllMovieList() {
        dialog.show()
        mService.getNewsList().enqueue(object : Callback<MutableList<Article>> {
            override fun onFailure(call: Call<MutableList<Article>>, t: Throwable) {

            }

            override fun onResponse(call: Call<MutableList<Article>>, response: Response<MutableList<Article>>) {
                adapter = NewsAdapter(context, response.body() as MutableList<Article>)
                adapter.notifyDataSetChanged()
                recyclerMovieList.adapter = adapter
                dialog.dismiss()


            }
        })
    }



}