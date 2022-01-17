package com.saidov.news2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saidov.news2022.view.FavoritesFragment
import com.saidov.news2022.view.HistoryFragment
import com.saidov.news2022.view.NewsFragment
import com.saidov.news2022.view.SettingsFragment


class MainActivity : AppCompatActivity() {
   // 9a08716b46e54472ae8e71c450b67d2c
    private lateinit var bottomNav : BottomNavigationView
    private lateinit var titleToolbar : TextView

    private val newsFragment = NewsFragment()
    private val historyFragment = HistoryFragment()
    private val favoritesFragment = FavoritesFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        replaceFragment(newsFragment)
        bottomNav = findViewById(R.id.bottomNav)
        titleToolbar = findViewById(R.id.toolbar_title)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.newsNav ->{
                    replaceFragment(newsFragment)
                    titleToolbar.text = "Home"
                }
                R.id.historyNav -> {
                    replaceFragment(historyFragment)
                    titleToolbar.text = "History"
                }
                R.id.favoriteNav ->{
                    replaceFragment(favoritesFragment)
                    titleToolbar.text = "Favorites"
                }
                R.id.settingsNav ->{
                    replaceFragment(settingsFragment)
                    titleToolbar.text = "Settings"
                }
            }
            true
        }


    }

    fun replaceFragment(fragment: Fragment){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView,fragment)
            transaction.commit()
        }
    }
}