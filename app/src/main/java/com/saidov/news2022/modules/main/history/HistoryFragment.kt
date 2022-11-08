package com.saidov.news2022.modules.main.history

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Organization.TITLE
import android.provider.MediaStore.Images.ImageColumns.DESCRIPTION
import android.view.View
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saidov.news2022.R
import com.saidov.news2022.core.callback.OnSearchListener
import com.saidov.news2022.core.fragment.BaseFragment
import com.saidov.news2022.modules.main.home.newsdetails.DetailFragment
import com.saidov.news2022.modules.main.home.ui.adapter.NewsAdapter
import com.saidov.news2022.modules.main.ui.model.ArticleModel
import com.saidov.news2022.modules.main.ui.vm.SharedViewModel
import com.saidov.news2022.other.Constants.Companion.PUBLISHED_AT
import com.saidov.news2022.other.Constants.Companion.URL
import com.saidov.news2022.other.Constants.Companion.URL_TO_IMAGE
import kotlin.properties.Delegates

class HistoryFragment : BaseFragment(R.layout.fragment_history), View.OnClickListener,
    View.OnLongClickListener, OnSearchListener {
    private var newsAdapter: NewsAdapter by Delegates.notNull()
    private var recyclerView: RecyclerView by Delegates.notNull()
    private var progressBar: ProgressBar by Delegates.notNull()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initObservers()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedViewModel.setToolbarName(getString(R.string.menu_history))
    }

    private fun initData() {
        val manager: LinearLayoutManager = GridLayoutManager(context, 1)
        progressBar.visibility = View.VISIBLE
        recyclerView.layoutManager = manager
        newsAdapter = NewsAdapter(this, this)
        recyclerView.adapter = newsAdapter
    }

    private fun initView() {
        recyclerView = findViewByID(R.id.recHistory)
        progressBar = findViewByID(R.id.progressBarHistory)
    }

    private fun initObservers() {
        sharedViewModel.allHistory.observe(viewLifecycleOwner) { articleList ->
            newsAdapter.differ.submitList(articleList.toList())
        }
        sharedViewModel.loadHistory()
        progressBar.isInvisible = true
    }

    private fun popupMenus(v: View, item: ArticleModel) {
        val popupMenus = PopupMenu(v.context, v)
        popupMenus.inflate(R.menu.menu_popup_delete_history)
        popupMenus.setOnMenuItemClickListener {
            if (it.itemId == R.id.deleteArticleHis) {
                sharedViewModel.removeHistory(item)
                showSnackBar(v,
                    getString(R.string.delete_news),
                    true,
                    getString(R.string.delete_news),
                    action = {
                        sharedViewModel.saveHistory(item)
                        sharedViewModel.loadHistory()
                    })
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
            true
        }
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
        transaction(R.id.fragmentContainerView, detailFragment, true)
    }


    override fun onClick(v: View?) {
        v?.let {
            val article = it.tag as ArticleModel
            sendData(article)
        }
    }

    override fun onLongClick(v: View?): Boolean {
        v?.let {
            val article = it.tag as ArticleModel
            popupMenus(v, article)
        }
        return true
    }

    override fun onSearch(query: String) {
        sharedViewModel.searchByHistory(query)
    }
}


