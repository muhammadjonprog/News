package com.saidov.news2022.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saidov.news2022.R
import com.saidov.news2022.vm.NewsViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav : BottomNavigationView
    private val newsFragment = NewsFragment()
    private val historyFragment = HistoryFragment()
    private val favoritesFragment = FavoritesFragment()
    private val settingsFragment = SettingsFragment()

//    lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(newsFragment)
        title = "Новости"
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setBackgroundDrawable(getDrawable(R.drawable.bg_round_bottom))

       // newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

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
        // Handle item selection
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