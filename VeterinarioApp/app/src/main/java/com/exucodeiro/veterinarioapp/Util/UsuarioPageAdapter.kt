package com.exucodeiro.veterinarioapp.Util

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.exucodeiro.veterinarioapp.AnimalListFragment
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.ProfissionalAddressFragment
import com.exucodeiro.veterinarioapp.UsuarioDetailFragment

/**
 * Created by vitor on 29/10/2017.
 */
class UsuarioPageAdapter(fm: FragmentManager, private val context: Context, val usuario: Usuario) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 ->  {
                val fragment = UsuarioDetailFragment()
                val bundle = Bundle()
                bundle.putSerializable("usuario",  usuario)
                fragment.arguments = bundle
                return fragment
            }
            1 ->  {
                val fragment = AnimalListFragment()
                val bundle = Bundle()
                bundle.putSerializable("usuario",  usuario)
                fragment.arguments = bundle
                return fragment
            }
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