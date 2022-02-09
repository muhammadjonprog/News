package com.saidov.news2022.modules.main.home.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.saidov.news2022.R
import com.saidov.news2022.core.fragment.BaseFragment
import com.saidov.news2022.modules.main.home.newsdetails.DetailFragment
import com.saidov.news2022.modules.main.home.ui.adapter.NewsAdapter
import com.saidov.news2022.modules.main.ui.callback.OnToolbarListener
import com.saidov.news2022.modules.main.ui.model.Article
import com.saidov.news2022.modules.main.ui.vm.MainViewModel
import com.saidov.news2022.repository.networkrepository.event.Resource
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CategoryNewsFragment(private val category : String, private val codeCountry : String) : BaseFragment(R.layout.fragment_category_news),
    View.OnLongClickListener, View.OnClickListener,
    OnToolbarListener {
    lateinit var newsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar

    private val viewModel by sharedViewModel<MainViewModel>()


    private fun initData(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewNews)
        progressBar = view.findViewById(R.id.pbNews)
        val manager: LinearLayoutManager = GridLayoutManager(context, 1)
        recyclerView.layoutManager = manager
        newsAdapter = NewsAdapter(this,this)
        recyclerView.adapter = newsAdapter
        progressBar.visibility = View.VISIBLE
    }

    private fun initViewModel(view : View){
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let { message->
                        Snackbar.make(view, "Ошибка : $message", Snackbar.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })
        viewModel.newsByCategory(category,codeCountry)
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
        isLoading= false
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        isLoading= true
    }

    var isLoading= false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData(view)
        initViewModel(view)
    }

    private fun popupMenus(v : View,item: Article){
        val popupMenus = PopupMenu(v.context,v)
        popupMenus.inflate(R.menu.menu_popup_add)
        popupMenus.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.addFavorite->{
                    viewModel.saveFavorite(item)
                    Snackbar.make(v, "Новости добавлен в избранные", Snackbar.LENGTH_SHORT).show()
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
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,detailFragment)?.addToBackStack(this::class.java.simpleName)?.commit()
    }

    override fun onLongClick(v: View?): Boolean {
        v?.let {
            val article = it.tag as Article
            popupMenus(v,article)
        }
        return true
    }

    override fun onClick(v: View?) {
        v?.let {
            val article = it.tag as Article
            sendData(article)
            viewModel.saveHistory(article)
        }
    }


    override fun setDisplayHomeEnabled(boolean: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setToolbarTitle(title: String) {
        TODO("Not yet implemented")
    }

}