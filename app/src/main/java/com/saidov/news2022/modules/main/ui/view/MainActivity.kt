package com.saidov.news2022.modules.main.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saidov.news2022.modules.main.favorite.FavoritesFragment
import com.saidov.news2022.modules.main.history.HistoryFragment
import com.saidov.news2022.modules.main.home.ui.view.NewsFragment
import com.saidov.news2022.modules.main.settings.view.SettingsFragment

import com.saidov.news2022.R
import com.saidov.news2022.core.activity.BaseActivity
import kotlin.properties.Delegates


class MainActivity : BaseActivity() {

    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Новости"
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setBackgroundDrawable(getDrawable(R.drawable.bg_round_bottom))

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.newsNav ->{
                    replaceFragment(NewsFragment())
                   title = "Новости"
                }
                R.id.historyNav -> {
                    replaceFragment(HistoryFragment())
                    title = "История"
                }
                R.id.favoriteNav ->{
                    replaceFragment(FavoritesFragment())
                    title = "Избранные"
                }
                R.id.settingsNav ->{
               replaceFragment(SettingsFragment())
                    title = "Настройки"
                }
            }
            true
        }
        bottomNav.selectedItemId = R.id.newsNav

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