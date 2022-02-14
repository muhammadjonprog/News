package com.saidov.news2022.core.activity

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by MUHAMMADJON SAIDOV on 08,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
open class BaseActivity : AppCompatActivity() {

//    private var doubleClick: Boolean = false
//
//    override fun onBackPressed() {
//        if (supportFragmentManager.backStackEntryCount > 0)
//            super.onBackPressed()
//        if (doubleClick) {
//            super.onBackPressed()
//        }
//        doubleClick = true
//    }

   protected open fun showLongToast(text: String) {
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
    }

    protected open fun showShortToast(text: String) {
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
    }


}