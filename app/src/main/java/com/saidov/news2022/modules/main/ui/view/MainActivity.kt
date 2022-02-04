package com.saidov.news2022.modules.main.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saidov.news2022.modules.main.favorite.FavoritesFragment
import com.saidov.news2022.modules.main.history.HistoryFragment
import com.saidov.news2022.modules.main.home.NewsFragment
import com.saidov.news2022.modules.main.settings.SettingsFragment
import com.saidov.news2022.modules.main.ui.vm.MainViewModel
import com.saidov.news2022.modules.main.ui.vm.MainViewModelProviderFactory
import com.saidov.news2022.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav : BottomNavigationView
    private val newsFragment = NewsFragment()
    private val historyFragment = HistoryFragment()
    private val favoritesFragment = FavoritesFragment()
    private val settingsFragment = SettingsFragment()
//    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(newsFragment)
        title = "Новости"
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setBackgroundDrawable(getDrawable(R.drawable.bg_round_bottom))



//        val viewModelProviderFactory = MainViewModelProviderFactory(application)
//        mainViewModel = ViewModelProvider(this,viewModelProviderFactory)
//            .get(MainViewModel::class.java)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.newsNav ->{
                    replaceFragment(newsFragment)
                   title = "Новости"
                }
                R.id.historyNav -> {
                    replaceFragment(historyFragment)
                    title = "История"
                }
                R.id.favoriteNav ->{
                    replaceFragment(favoritesFragment)
                    title = "Избранные"
                }
                R.id.settingsNav ->{
                    replaceFragment(settingsFragment)
                    title = "Настройки"
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_menu -> {
                true
            }
            R.id.notification_menu -> {
                Toast.makeText(this,"Notification",Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(fragment: Fragment?){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView,fragment)
            transaction.commit()
        }
    }
}