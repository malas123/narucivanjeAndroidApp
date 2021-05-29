package com.example.diplomskitest1.pager_adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.diplomskitest1.ui.main.FragmentKorpa
import com.example.diplomskitest1.ui.main.MainFragment

class ViewPager2Adapter(fm:FragmentManager,lc:Lifecycle,fragments:MutableList<Fragment>) : FragmentStateAdapter(fm,lc) {

    var fragments: MutableList<Fragment>

    init {
        this.fragments = fragments
    }
    override fun getItemCount(): Int {

    return fragments.count()


    }

    override fun createFragment(position: Int): Fragment {

        return fragments[position]
    }


}