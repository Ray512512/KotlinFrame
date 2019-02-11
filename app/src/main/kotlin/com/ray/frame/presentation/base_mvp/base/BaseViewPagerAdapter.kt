package com.ray.frame.presentation.base_mvp.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*


class BaseViewPagerAdapter(private val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFrag(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitleList[position]
    }

    fun clear() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        for (f in this.mFragmentList) {
            fragmentTransaction.remove(f)
        }
        fragmentTransaction.commit()
        fragmentManager.executePendingTransactions()
        mFragmentList.clear()
        mFragmentTitleList.clear()
        notifyDataSetChanged()

    }
}
