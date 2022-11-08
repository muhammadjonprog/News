package com.saidov.news2022.core.activity

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.saidov.news2022.R

/**
 * Created by MUHAMMADJON SAIDOV on 08,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

abstract class BaseActivity : AppCompatActivity() {

    protected fun transaction(
        container_id: Int, fragment: Fragment, addToBackStack: Boolean = false, tag: String? = null
    ) {
        supportFragmentManager.beginTransaction().apply {
            replace(container_id, fragment, tag)
            if (addToBackStack) addToBackStack(tag)
            commit()
        }
    }

    protected fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }
}