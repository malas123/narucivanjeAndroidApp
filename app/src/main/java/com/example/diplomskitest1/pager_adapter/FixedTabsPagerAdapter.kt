package com.example.diplomskitest1.pager_adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FixedTabsPagerAdapter :FragmentPagerAdapter
{
    var fragments:MutableList<Fragment>
    constructor(fm:FragmentManager,fragments:MutableList<Fragment>):super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    {
        this.fragments = fragments
    }
    override fun getItem(position: Int): Fragment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}