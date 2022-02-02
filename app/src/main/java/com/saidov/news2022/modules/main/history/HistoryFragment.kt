package com.saidov.news2022.modules.main.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.saidov.news2022.R
import com.saidov.news2022.modules.main.home.ui.adapter.NewsAdapter
import com.saidov.news2022.modules.main.ui.model.Article
import com.saidov.news2022.modules.main.home.newsdetails.DetailFragment
import com.saidov.news2022.modules.main.ui.view.MainActivity
import com.saidov.news2022.modules.main.ui.vm.MainViewModel

class HistoryFragment : Fragment(), View.OnClickListener, View.OnLongClickListener {
    lateinit var newsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_history, container, false)

        initData(view)
        initViewModel()
        return view
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

    private fun initViewModel() {
        viewModel = (activity as MainActivity).mainViewModel
        viewModel.allHistory.observe(viewLifecycleOwner, {
            newsAdapter.differ.submitList(it.toList())
        })
        viewModel.loadHistory()
        progressBar.visibility = View.INVISIBLE
    }

    override fun onClick(v: View?) {
        v?.let {
            val article = it.tag as Article
            sendData(article)
        }

    }

    override fun onLongClick(v: View?): Boolean {
        v?.let {
            val article = it.tag as Article
            popupMenus(v, article)
        }
        return true
    }

    private fun popupMenus(v: View, item: Article) {
        val popupMenus = PopupMenu(v.context, v)
        popupMenus.inflate(R.menu.menu_popup_delete)
        popupMenus.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteArticle -> {
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

    private fun sendData(item: Article) {
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
}


