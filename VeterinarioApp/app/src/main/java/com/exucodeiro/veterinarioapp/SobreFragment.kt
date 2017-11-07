package com.exucodeiro.veterinarioapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exucodeiro.veterinarioapp.Models.Sobre
import com.exucodeiro.veterinarioapp.Util.SobreAdapter
import kotlinx.android.synthetic.main.fragment_sobre.*

class SobreFragment : Fragment() {
    private var adapter: SobreAdapter? = null
    private var sobreList: ArrayList<Sobre> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_sobre, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        activity.title = getString(R.string.info)

        sobreList.add(Sobre ("Kotlin\nAndroid Studio 3.0", R.mipmap.ic_build_black_24dp))
        sobreList.add(Sobre ("Exu Codeiro", R.mipmap.ic_group_work_black_24dp))
        sobreList.add(Sobre ("Pìcasso, Circle ImageView, Fuel, Jackson", R.mipmap.ic_extension_black_24dp))
        sobreList.add(Sobre ("https://github.com/Vitor-Xavier/VeterinarioApp", R.mipmap.ic_code_black_24dp))
        sobreList.add(Sobre ("https://github.com/Vitor-Xavier/VeterinarioAPI", R.mipmap.ic_code_black_24dp))
        adapter = SobreAdapter(sobreList, activity)
        listSobre.adapter = adapter

        super.onActivityCreated(savedInstanceState)
    }

}
