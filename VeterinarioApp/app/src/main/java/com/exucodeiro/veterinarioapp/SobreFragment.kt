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
    private lateinit var adapter: SobreAdapter
    private var sobreList: ArrayList<Sobre> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        sobreList.add(Sobre ("Kotlin - Android Studio 3.0.1\nC# - Visual Studio 2017", R.mipmap.ic_build_black_24dp))
        sobreList.add(Sobre ("Exu Codeiro", R.mipmap.ic_group_work_black_24dp))
        sobreList.add(Sobre ("Anko, Pìcasso, Circle ImageView, Fuel & Jackson\nEntity Framework, Swagger & Newtonsoft.Json", R.mipmap.ic_extension_black_24dp))
        sobreList.add(Sobre ("App\nhttps://github.com/Vitor-Xavier/VeterinarioApp", R.mipmap.ic_code_black_24dp))
        sobreList.add(Sobre ("Api\nhttps://github.com/Vitor-Xavier/VeterinarioAPI", R.mipmap.ic_code_black_24dp))
        adapter = SobreAdapter(sobreList, activity)


        return inflater!!.inflate(R.layout.fragment_sobre, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity.title = getString(R.string.info)
        listSobre.adapter = adapter
    }

}
