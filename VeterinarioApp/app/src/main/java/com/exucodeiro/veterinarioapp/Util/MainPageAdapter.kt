package com.exucodeiro.veterinarioapp.Util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.ProfissionalListFragment
import com.exucodeiro.veterinarioapp.ProfissionalMapFragment

class MainPageAdapter(fm: FragmentManager, private val profissionais: List<Profissional>, private val lat: Double, private val lng: Double) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position == 1) {
            true -> return ProfissionalMapFragment.newInstance(ArrayList(profissionais), lat, lng)
            false -> return ProfissionalListFragment.newInstance(ArrayList(profissionais))
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