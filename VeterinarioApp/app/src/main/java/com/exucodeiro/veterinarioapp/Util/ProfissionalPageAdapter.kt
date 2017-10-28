package com.exucodeiro.veterinarioapp.Util

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.exucodeiro.veterinarioapp.ProfissionalAddressFragment
import com.exucodeiro.veterinarioapp.ProfissionalDetailFragment

/**
 * Created by vitor on 27/10/2017.
 */
class ProfissionalPageAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ProfissionalDetailFragment()
            1 -> return ProfissionalAddressFragment()
        }
        return ProfissionalDetailFragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> return "Detalhes"
            1 -> return "Endereço"
            else -> return null
        }
    }

}