package com.saidov.news2022.modules.main.favorite

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
import com.saidov.news2022.R
import com.saidov.news2022.core.callback.OnSearchListener
import com.saidov.news2022.core.callback.OnToolBarChangedListener
import com.saidov.news2022.core.fragment.BaseFragment

import com.saidov.news2022.modules.main.home.ui.adapter.NewsAdapter
import com.saidov.news2022.modules.main.ui.model.ArticleModel
import com.saidov.news2022.modules.main.home.newsdetails.DetailFragment
import com.saidov.news2022.modules.main.ui.view.MainActivity
import com.saidov.news2022.modules.main.ui.vm.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites),
    View.OnClickListener, View.OnLongClickListener, OnToolBarChangedListener, OnSearchListener {

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
        listener?.setToolbarName("Избранные")
    }

    override fun onSearch(query: String) {
            viewModel.searchByFavorite(query)

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

    private fun initData(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerFav)
        progressBar = view.findViewById<ProgressBar>(R.id.progressBarFav)
        val manager: LinearLayoutManager = GridLayoutManager(context, 1)
        progressBar.visibility = View.VISIBLE
        recyclerView.layoutManager = manager
        newsAdapter = NewsAdapter(this, this)
        recyclerView.adapter = newsAdapter
    }

    private fun initObservers() {
        viewModel.allFavorite.observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it.toList())
        }
        viewModel.loadFavorite()
        progressBar.visibility = View.INVISIBLE
    }

    private fun popupMenus(v: View, item: ArticleModel) {
        val popupMenus = PopupMenu(v.context, v)
        popupMenus.inflate(R.menu.menu_popup_delete_favorites)
        popupMenus.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteArticle -> {
                    viewModel.removeFavorite(item)
                    Snackbar.make(v, "Новости успешно удалено ", Snackbar.LENGTH_LONG).apply {
                        setAction("Отмена") {
                            viewModel.saveFavorite(item)
                            viewModel.loadFavorite()
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
        val detailFragment = DetailFragment.newInstance(this)
        detailFragment.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, detailFragment)
            ?.addToBackStack(this::class.java.simpleName)?.commit()
    }

    override fun setToolbarName(title: String) {
        activity?.actionBar?.title = title
    }

    override fun setToolBarBackVisibility(status: Boolean) {
        activity?.actionBar?.setDisplayHomeAsUpEnabled(status)
    }

    override fun clearToolBar() {
        TODO("Not yet implemented")
    }

    companion object {
        fun newInstance(listener: OnToolBarChangedListener?): FavoritesFragment {
            val fragment: FavoritesFragment = FavoritesFragment()
            fragment.listener = listener
            return fragment
        }
    }

}