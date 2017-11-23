package com.exucodeiro.veterinarioapp.Util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.ProfissionalAddressFragment
import com.exucodeiro.veterinarioapp.ProfissionalDetailFragment

class ProfissionalPageAdapter(fm: FragmentManager, val profissional: Profissional) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position == 1) {
            true -> return ProfissionalAddressFragment.newInstance(profissional)
            false -> return ProfissionalDetailFragment.newInstance(profissional)
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position == 1) {
            true -> return "Endereço"
            false -> return "Detalhes"
        }
    }

}