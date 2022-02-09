package com.saidov.news2022.core.activity

import androidx.appcompat.app.AppCompatActivity

/**
 * Created by MUHAMMADJON SAIDOV on 08,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
open class BaseActivity : AppCompatActivity() {
     var doubleClick : Boolean = true

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0)
        super.onBackPressed()
        if(doubleClick){
            super.onBackPressed()
        }
    }

}