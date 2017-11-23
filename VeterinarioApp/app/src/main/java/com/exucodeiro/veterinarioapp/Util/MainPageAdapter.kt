package com.exucodeiro.veterinarioapp.Util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.exucodeiro.veterinarioapp.ProfissionalListFragment
import com.exucodeiro.veterinarioapp.ProfissionalMapFragment

class MainPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position == 1) {
            true -> return ProfissionalMapFragment()
            false -> return ProfissionalListFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position == 1) {
            true -> return "Mapa"
            false -> return "Lista"
        }
    }
}