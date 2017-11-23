package com.exucodeiro.veterinarioapp.Util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.exucodeiro.veterinarioapp.AnimalListFragment
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.UsuarioDetailFragment

class UsuarioPageAdapter(fm: FragmentManager, val usuario: Usuario) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position == 1) {
            true -> return AnimalListFragment.newInstance(usuario)
            false -> return UsuarioDetailFragment.newInstance(usuario)
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position == 1) {
            true -> return "Detalhes"
            false -> return "Animais"
        }
    }

}