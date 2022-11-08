package com.saidov.news2022.modules.main.ui.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saidov.news2022.R
import com.saidov.news2022.core.activity.BaseActivity
import com.saidov.news2022.core.callback.OnSearchListener
import com.saidov.news2022.modules.main.favorite.FavoritesFragment
import com.saidov.news2022.modules.main.history.HistoryFragment
import com.saidov.news2022.modules.main.home.ui.view.HomeFragment
import com.saidov.news2022.modules.main.settings.view.SettingsFragment
import com.saidov.news2022.modules.main.ui.vm.SharedViewModel
import kotlin.properties.Delegates


class MainActivity : BaseActivity(), SearchView.OnQueryTextListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var navView: BottomNavigationView by Delegates.notNull()
    private var searchView: SearchView by Delegates.notNull()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initListener()
        initObservers()
        onNavigationItemSelected(navView.menu.getItem(0))
    }

    private fun initView() {
        navView = findViewById(R.id.bottomNav)
    }

    private fun initListener() {
        navView.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        navView.setOnNavigationItemSelectedListener(this)
    }

    private fun initObservers() {
        sharedViewModel.toolbarTitle.observe(this) { title ->
            supportActionBar?.title = title
        }

        sharedViewModel.toolbarBackVisibility.observe(this) { status ->
            supportActionBar?.setDisplayHomeAsUpEnabled(status)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val menuItem: MenuItem = menu.findItem(R.id.search_menu)
        val view = menuItem.actionView
        searchView = view as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.notification_menu) showToast("Notification", Toast.LENGTH_SHORT)
        return true
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
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val bottomNavigationTab = mapOf(R.id.newsNav to {
            transaction(
                R.id.fragmentContainerView, HomeFragment()
            )
        }, R.id.historyNav to {
            transaction(
                R.id.fragmentContainerView, HistoryFragment()
            )
        }, R.id.favoriteNav to {
            transaction(
                R.id.fragmentContainerView, FavoritesFragment()
            )
        }, R.id.settingsNav to {
            transaction(
                R.id.fragmentContainerView, SettingsFragment()
            )
        })
        bottomNavigationTab[item.itemId]?.invoke()
        return true
    }
}



