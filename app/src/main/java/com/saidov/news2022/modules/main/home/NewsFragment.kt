package com.saidov.news2022.modules.main.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.saidov.news2022.R
import com.saidov.news2022.modules.main.home.newsdetails.DetailFragment
import com.saidov.news2022.modules.main.home.ui.adapter.NewsAdapter
import com.saidov.news2022.modules.main.ui.model.Article
import com.saidov.news2022.modules.main.ui.view.MainActivity
import com.saidov.news2022.modules.main.ui.vm.MainViewModel
import com.saidov.news2022.repository.networkrepository.event.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment(), View.OnLongClickListener, View.OnClickListener {
    lateinit var newsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var tab : TabLayout
    private  val viewModel by viewModel<MainViewModel>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_news, container, false)
        initData(view)

        initViewModel(view)

        return view
    }
    private fun initData(view: View) {
        tab = view.findViewById(R.id.tabLayout)
//        viewPager = view.findViewById(R.id.viewpager)
//        viewPager.setCurrentItem(0)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewNews)
        progressBar = view.findViewById(R.id.pbNews)
        val manager: LinearLayoutManager = GridLayoutManager(context, 1)
        recyclerView.layoutManager = manager
        newsAdapter = NewsAdapter(this,this)
        recyclerView.adapter = newsAdapter
        progressBar.visibility = View.VISIBLE
//        adapterPager = ViewPagerAdapter(requireFragmentManager())
//        for (i in 0..5) {
//            adapterPager.addFragment(Fragment(i), "ONE")
//            viewPager.adapter = adapterPager
//        }
//        tab.setupWithViewPager(viewPager);
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViewModel(view : View){
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        Snackbar.make(view, "Данные успешно загружены", Snackbar.LENGTH_SHORT).show()
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let { message->
                        Log.e("NEWS", "Ошибка : $message" )
                        Snackbar.make(view, "Ошибка : $message", Snackbar.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })
        viewModel.newsApi()
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
}


