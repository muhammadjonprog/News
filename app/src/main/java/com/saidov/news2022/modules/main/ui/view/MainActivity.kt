package com.saidov.news2022.modules.main.ui.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saidov.news2022.R
import com.saidov.news2022.core.activity.BaseActivity
import com.saidov.news2022.core.callback.OnSearchListener
import com.saidov.news2022.core.callback.OnToolBarChangedListener
import com.saidov.news2022.modules.main.favorite.FavoritesFragment
import com.saidov.news2022.modules.main.history.HistoryFragment
import com.saidov.news2022.modules.main.home.ui.view.HomeFragment
import com.saidov.news2022.modules.main.settings.view.SettingsFragment
import com.saidov.news2022.modules.main.ui.vm.MainViewModelProviderFactory
import com.saidov.news2022.modules.main.ui.vm.SharedViewModel


class MainActivity : BaseActivity(), SearchView.OnQueryTextListener, OnToolBarChangedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navView: BottomNavigationView
    private lateinit var searchView: SearchView
    private val TAG = "A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, "Activity created")

        navView = findViewById(R.id.bottomNav)
        navView.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        navView.setOnNavigationItemSelectedListener(this)


        //ToDo: Бехтар мешавад дар дарони onCreate не, дар дарони худи MainActivity OnNavigationItemSelectedListener-ро эълон кунид
        onNavigationItemSelected(navView.menu.getItem(0))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val menuItem: MenuItem = menu.findItem(R.id.search_menu)
        val view: View = menuItem.actionView
        searchView = view as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.notification_menu -> {
                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        fragment?.let {
            transaction.replace(R.id.fragmentContainerView, it, javaClass.simpleName)
        }
        transaction.commit()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            val fragment: Fragment? = supportFragmentManager.findFragmentByTag(javaClass.simpleName)
            fragment as OnSearchListener?
            if (query.toString().isNotEmpty()) {
                fragment?.onSearch(query = it)
            }
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {

        return true
    }


    override fun setToolbarName(title: String) {
        supportActionBar?.title = title
    }

    override fun setToolBarBackVisibility(status: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(status)
    }

    @SuppressLint("RestrictedApi")
    override fun clearToolBar() {
        val item = findViewById<View>(R.id.search_menu) as MenuItem
        item.isVisible = false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.newsNav -> {
                replaceFragment(HomeFragment.newInstance(listener = this))
            }
            R.id.historyNav -> {
                replaceFragment(HistoryFragment.newInstance(listener = this))
            }
            R.id.favoriteNav -> {
                replaceFragment(FavoritesFragment.newInstance(listener = this))
            }
            R.id.settingsNav -> {
                replaceFragment(SettingsFragment.newInstance(listener = this))
            }
        }
        return true
    }
}



