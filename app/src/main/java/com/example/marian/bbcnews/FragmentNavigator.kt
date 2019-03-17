package com.example.marian.bbcnews

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentNavigator(@IdRes val containerId: Int, val fragmentManager: FragmentManager) {

    fun loadFragment(fragment: Fragment, tag: String) {
        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction()
                .add(containerId, fragment, tag)
                .commitNow()
        } else {
            fragmentManager.beginTransaction()
                .show(fragment)
                .commitNow()
        }
    }

    fun hideFragment(tag: String?) {
        tag?.let {
            var fragment = fragmentManager.findFragmentByTag(tag)
            fragment?.let {
                fragmentManager.beginTransaction()
                    .hide(fragment)
                    .commitNow()
            }
        }
    }

    fun destroyFragment(tag: String?) {
        tag?.let {
            var fragment = fragmentManager.findFragmentByTag(tag)
            fragment?.let {
                fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commitNow()
            }
        }
    }
}