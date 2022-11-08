package com.saidov.news2022.modules.main.home.ui.view

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
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
import com.saidov.news2022.modules.main.ui.vm.SharedViewModel
import com.saidov.news2022.other.Constants.Companion.DESCRIPTION
import com.saidov.news2022.other.Constants.Companion.PUBLISHED_AT
import com.saidov.news2022.other.Constants.Companion.TITLE
import com.saidov.news2022.other.Constants.Companion.URL
import com.saidov.news2022.other.Constants.Companion.URL_TO_IMAGE
import com.saidov.news2022.repository.networkrepository.event.Resource
import kotlin.properties.Delegates

class ViewPagerFragment() : BaseFragment(R.layout.fragment_viewpager_news),
    View.OnLongClickListener, View.OnClickListener, OnSearchListener {
    private var newsAdapter: NewsAdapter by Delegates.notNull()
    private var recyclerView: RecyclerView by Delegates.notNull()
    private var progressBar: ProgressBar by Delegates.notNull()
    private var category: String = ""
    private var codeCountry: String = ""
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData()
        initObservers(view)
    }

    private fun initData() {
        initView()
        val manager: LinearLayoutManager = GridLayoutManager(context, 1)
        recyclerView.layoutManager = manager
        newsAdapter = NewsAdapter(this, this)
        recyclerView.adapter = newsAdapter
    }

    private fun initView() {
        recyclerView = findViewByID(R.id.recyclerViewNews)
        progressBar = findViewByID(R.id.pbNews)
    }

    private fun initObservers(view: View) {
        viewModel.news[category]?.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    progressBar.isInvisible = true
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articleModels.toList())
                    }
                }
                is Resource.Error -> {
                    progressBar.isInvisible = true
                    response.message?.let { message ->
                        Snackbar.make(view, "Ошибка : $message", Snackbar.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    progressBar.isVisible = true
                }
            }
        }
        viewModel.newsByCategory(category, codeCountry)
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
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java).invoke(menu, true)
    }

    private fun sendData(item: ArticleModel) {
        val bundle = Bundle()
        bundle.putString(TITLE, item.title)
        bundle.putString(DESCRIPTION, item.description)
        bundle.putString(URL_TO_IMAGE, item.urlToImage)
        bundle.putString(URL, item.url)
        bundle.putString(PUBLISHED_AT, item.publishedAt)
        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle
        transaction(R.id.fragmentContainerView, detailFragment)
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
        fun newInstance(category: String, codeCountry: String) = ViewPagerFragment().apply {
            this.category = category
            this.codeCountry = codeCountry
        }
    }
}
