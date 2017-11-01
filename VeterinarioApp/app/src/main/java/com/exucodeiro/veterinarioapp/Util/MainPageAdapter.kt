package com.exucodeiro.veterinarioapp.Util

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.exucodeiro.veterinarioapp.ProfissionalListFragment
import com.exucodeiro.veterinarioapp.ProfissionalMapFragment

/**
 * Created by vitor on 28/10/2017.
 */
class MainPageAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ProfissionalListFragment()
            1 -> return ProfissionalMapFragment()
        }
        return ProfissionalListFragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> return "Lista"
            1 -> return "Mapa"
            else -> return null
        }
    }
}