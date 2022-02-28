package com.saidov.news2022.modules.main.history

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.saidov.news2022.modules.main.home.ui.adapter.NewsAdapter
import com.saidov.news2022.modules.main.ui.model.ArticleModel
import com.saidov.news2022.modules.main.home.newsdetails.DetailFragment
import com.saidov.news2022.modules.main.ui.vm.SharedViewModel
import com.saidov.news2022.R
import com.saidov.news2022.core.callback.OnSearchListener
import com.saidov.news2022.core.callback.OnToolBarChangedListener
import com.saidov.news2022.core.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HistoryFragment : BaseFragment(R.layout.fragment_history),

    View.OnClickListener, View.OnLongClickListener, OnSearchListener {
    lateinit var newsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    var listener: OnToolBarChangedListener? = null
    private val viewModel: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(view)
        initObservers()
        //ToDO:Ивазкунии title бехтаращ дар даркорни onAttach шаавад бехтар, так как onAttach як маротиба чег зада мешавад
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener?.setToolbarName("История")
    }

    override fun onSearch(query: String) {
        viewModel.searchByHistory(query)
    }

    private fun initData(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recHistory)
        progressBar = view.findViewById<ProgressBar>(R.id.progressBarHistory)
        val manager: LinearLayoutManager = GridLayoutManager(context, 1)
        progressBar.visibility = View.VISIBLE
        recyclerView.layoutManager = manager
        newsAdapter = NewsAdapter(this, this)
        recyclerView.adapter = newsAdapter
    }

    private fun initObservers() {
        viewModel.allHistory.observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it.toList())
        }
        viewModel.loadHistory()
        progressBar.visibility = View.INVISIBLE
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

    private fun popupMenus(v: View, item: ArticleModel) {
        val popupMenus = PopupMenu(v.context, v)
        popupMenus.inflate(R.menu.menu_popup_delete_history)
        popupMenus.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteArticleHis -> {
                    viewModel.removeHistory(item)
                    Snackbar.make(v, "Новости успешно удалено ", Snackbar.LENGTH_LONG).apply {
                        setAction("Отмена") {
                            viewModel.saveHistory(item)
                            viewModel.loadHistory()
                        }
                        show()
                    }
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
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, detailFragment)
            ?.addToBackStack(this::class.java.simpleName)?.commit()
    }

    companion object {
        fun newInstance(listener: OnToolBarChangedListener?): HistoryFragment {
            val fragment: HistoryFragment = HistoryFragment()
            fragment.listener = listener
            return fragment
        }
    }


}


