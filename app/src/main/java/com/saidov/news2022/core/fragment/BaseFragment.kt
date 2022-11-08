package com.saidov.news2022.core.fragment

import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.saidov.news2022.R


/**
 * Created by MUHAMMADJON SAIDOV on 06,февраль,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    protected fun transaction(
        container_id: Int, fragment: Fragment, addToBackStack: Boolean = false, tag: String? = null
    ) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment, tag)
            if (addToBackStack) addToBackStack(tag)
            commit()
        }
    }

    protected fun <T : View> findViewByID(@IdRes id: Int): T {
        return requireView().findViewById(id)
    }

    protected fun showToast(message: String, duration: Int) {
        Toast.makeText(requireContext(), message, duration).show()
    }

    protected fun showSnackBar(
        v: View,
        message: String,
        hasAction: Boolean = false,
        actionTitle: String,
        action: (() -> Unit)? = null
    ) {
        Snackbar.make(v, message, Snackbar.LENGTH_LONG).apply {
            if (hasAction) setAction(actionTitle) {
                action?.let {
                    it()
                }
            }
            show()
        }
    }
}




