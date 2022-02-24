package com.saidov.news2022.modules.main.home.ui.view

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.saidov.news2022.R
import com.saidov.news2022.core.callback.OnSearchListener
import com.saidov.news2022.core.fragment.BaseFragment
import com.saidov.news2022.modules.main.home.newsdetails.DetailFragment
import com.saidov.news2022.modules.main.home.ui.adapter.NewsAdapter
import com.saidov.news2022.modules.main.ui.model.ArticleModel
import com.saidov.news2022.modules.main.ui.view.MainActivity
import com.saidov.news2022.modules.main.ui.vm.SharedViewModel
import com.saidov.news2022.repository.networkrepository.event.Resource
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ViewPagerFragment() : BaseFragment(R.layout.fragment_viewpager_news),
    View.OnLongClickListener, View.OnClickListener, OnSearchListener {
    lateinit var newsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    private var category: String = ""
    private var codeCountry: String = ""
    private val viewModel: SharedViewModel by sharedViewModel()


    private fun initData(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewNews)
        progressBar = view.findViewById(R.id.pbNews)
        val manager: LinearLayoutManager = GridLayoutManager(context, 1)
        recyclerView.layoutManager = manager
        newsAdapter = NewsAdapter(this, this)
        recyclerView.adapter = newsAdapter
    }

    private fun initObservers(view: View) {
        viewModel.newsLiveDataMap[category]?.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articleModels.toList())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Snackbar.make(view, "Ошибка : $message", Snackbar.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }

            }
        })
        viewModel.newsByCategory(category, codeCountry)
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData(view)
        initObservers(view)
    }

    override fun onSearch(query: String) {
        viewModel.searchByTitle(queryInTitle = query, key = category)
    }

    private fun popupMenus(v: View, item: ArticleModel) {
        val popupMenus = PopupMenu(v.context, v)
        popupMenus.inflate(R.menu.menu_popup_add)
        popupMenus.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.addFavorite -> {
                    viewModel.saveFavorite(item)
                    true
                }
                else -> true
            }
        }
        popupMenus.show()
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenus)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)
    }

    private fun sendData(item: ArticleModel) {
        val bundle = Bundle()
        bundle.putString("title", item.title)
        bundle.putString("description", item.description)
        bundle.putString("urlToImage", item.urlToImage)
        bundle.putString("url", item.url)
        bundle.putString("publishedAt", item.publishedAt)
        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, detailFragment)
            .addToBackStack(this::class.java.simpleName).commit()
    }

    override fun onLongClick(v: View?): Boolean {
        v?.let {
            val article = it.tag as ArticleModel
            popupMenus(v, article)
        }
        return true
    }

    override fun onClick(v: View?) {
        v?.let {
            val article = it.tag as ArticleModel
            sendData(article)
            viewModel.saveHistory(article)
        }
    }

    companion object {
        fun newInstance(category: String, codeCountry: String): ViewPagerFragment {
            val fragment: ViewPagerFragment = ViewPagerFragment()
            fragment.category = category
            fragment.codeCountry = codeCountry
            return fragment
        }
    }
}
