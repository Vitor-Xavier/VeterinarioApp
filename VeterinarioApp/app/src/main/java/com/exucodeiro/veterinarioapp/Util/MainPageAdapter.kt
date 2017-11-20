package com.exucodeiro.veterinarioapp.Util

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.ProfissionalListFragment
import com.exucodeiro.veterinarioapp.ProfissionalMapFragment
import com.exucodeiro.veterinarioapp.R
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.google.android.gms.maps.MapFragment
import org.jetbrains.anko.async
import java.util.ArrayList

/**
 * Created by vitor on 28/10/2017.
 */
class MainPageAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                val fragment = ProfissionalListFragment()
                return fragment
            }
            1 -> {
                val fragment = ProfissionalMapFragment()
                val bundle = Bundle()
                val profissionalService = ProfissionalService()
                async {
                    val list = profissionalService.getProfissionais(-21.1767, -47.8208) as ArrayList<Profissional>
                    bundle.putSerializable("profissionais", list)
                }
                fragment.arguments = bundle
                return fragment
            }
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