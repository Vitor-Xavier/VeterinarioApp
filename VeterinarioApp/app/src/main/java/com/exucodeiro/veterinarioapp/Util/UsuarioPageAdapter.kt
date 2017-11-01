package com.exucodeiro.veterinarioapp.Util

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.exucodeiro.veterinarioapp.AnimalListFragment
import com.exucodeiro.veterinarioapp.ProfissionalAddressFragment

/**
 * Created by vitor on 29/10/2017.
 */
class UsuarioPageAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ProfissionalAddressFragment()
            1 -> return AnimalListFragment()
        }
        return ProfissionalAddressFragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> return "Detalhes"
            1 -> return "Animais"
            else -> return null
        }
    }

}