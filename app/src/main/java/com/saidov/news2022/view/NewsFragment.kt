package com.saidov.news2022.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saidov.news2022.R
import com.saidov.news2022.adapter.NewsAdapter
import com.saidov.news2022.adapter.NewsAdapterCallBack
import com.saidov.news2022.model.Article
import com.saidov.news2022.model.NewsResponse
import com.saidov.news2022.vm.NewsViewModel


class NewsFragment : Fragment(), NewsAdapterCallBack {

    lateinit var newsNewsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var viewModel : NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_news, container, false)
        initData(view)
        initViewModel()
        return view
    }
    private fun initData(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewNews)
        progressBar = view.findViewById(R.id.pbNews)
        val manager: LinearLayoutManager = GridLayoutManager(context, 1)
        recyclerView.layoutManager = manager
        newsNewsAdapter = NewsAdapter()
        newsNewsAdapter.setCallBack(this)
        recyclerView.adapter = newsNewsAdapter
        progressBar.visibility = View.VISIBLE
    }
    private fun initViewModel(){
        //val viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        viewModel= (activity as MainActivity).newsViewModel
        viewModel.getNewsObserver().observe(viewLifecycleOwner, Observer<NewsResponse> {
            if (it != null){
                newsNewsAdapter.updatedData(it.articles)
                progressBar.visibility = View.INVISIBLE
                Log.i("NEWS",it.articles.get(1).title)
            }else{
                Toast.makeText(activity,"Error",Toast.LENGTH_SHORT).show()
            }
        })
        if (hasInternetConnection()){
            viewModel.makeApiCall("business")
        }else {
            Toast.makeText(context, "Нет подключение к интернету", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
        }

    }

    private fun hasInternetConnection(): Boolean{
        val connectivityManager= activity?.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.activeNetwork?: return false
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        val capabilities= connectivityManager.getNetworkCapabilities(activeNetwork)?: return false

        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)-> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)-> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
            else -> false
        }
    }

    override fun itemOnClickListener(item: Article) {
       sendData(item)
    }

    override fun itemOnLongClickListener(item: Article,v : View) {
        popupMenus(v)
    }

    private fun popupMenus(v : View){
        val popupMenus = PopupMenu(v.context,v)
        popupMenus.inflate(R.menu.menu_popup)
        popupMenus.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.addFavorite->{
                    Toast.makeText(v.context,"Click",Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }
        popupMenus.show()
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenus)
        menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
            .invoke(menu,true)

    }

    private fun sendData(item: Article){
        val bundle = Bundle()
        bundle.putString("title",item.title)
        bundle.putString("description",item.description)
        bundle.putString("urlToImage",item.urlToImage)
        bundle.putString("url",item.url)
        bundle.putString("publishedAt",item.publishedAt)
        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,detailFragment)?.commit()
    }
}


