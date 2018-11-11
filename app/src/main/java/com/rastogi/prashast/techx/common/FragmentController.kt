package com.rastogi.prashast.techx.common

import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import java.util.HashMap

class FragmentController(val fragmentManager: FragmentManager, private val defaultTag: String) {

    private val fragments = HashMap<String, FragmentTemplate>()

    interface FragmentInstance {
        fun get(): Fragment
    }

    inner class FragmentTemplate (
        val tag: String,
        private val titleResId: Int,
        private val instance: FragmentInstance
    ) {

        fun getTitleResId(): Int {
            return if (titleResId > 0) titleResId else -1
        }

        fun inflate(containerViewId: Int, addToBackStack: Boolean) {
            var fragment = fragmentManager.findFragmentByTag(tag)
            if (fragment == null)
                fragment = instance.get()
            if (!fragment.isAdded) {
                if (addToBackStack)
                    addToBackStackAndStart(containerViewId, fragment)
                else
                    addAndStart(containerViewId, fragment)
            }
        }

        fun removeFragment() {
            val fragment = fragmentManager.findFragmentByTag(tag)
            if (fragment != null)
                fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
        }

        private fun addAndStart(containerViewId: Int, fragment: Fragment) {
            Log.e("fragment added :" + fragment.toString(), tag)
            fragmentManager.beginTransaction().replace(containerViewId, fragment, tag).commitAllowingStateLoss()
        }

        private fun addToBackStackAndStart(containerViewId: Int, fragment: Fragment) {
            Log.e("fragment added :" + fragment.toString(), tag)
            fragmentManager.beginTransaction().replace(containerViewId, fragment, tag).addToBackStack(fragment.tag)
                .commitAllowingStateLoss()
        }

        fun `is`(other: String): Boolean {
            return tag == other
        }
    }

    fun add(tag: String, titleResId: Int, instance: FragmentInstance) {
        Log.e("add frag", tag)
        fragments[tag] = FragmentTemplate(tag, titleResId, instance)
    }

    fun getCurrentFragment(intent: Intent): FragmentTemplate? {
        var currentTag = defaultTag
        for (tag in fragments.keys) {
            if (intent.hasExtra(tag)) {
                currentTag = tag
                break
            }
        }
        return fragments[currentTag]
    }

    fun getCurrentFragment(key: String): FragmentTemplate? {
        var currentTag = defaultTag

        for (tag in fragments.keys) {
            if (key == tag) {
                currentTag = tag
                break
            }
        }
        return fragments[currentTag]
    }
}
