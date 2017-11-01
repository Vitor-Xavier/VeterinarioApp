package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.exucodeiro.veterinarioapp.Models.Sobre
import com.exucodeiro.veterinarioapp.Util.SobreAdapter
import kotlinx.android.synthetic.main.activity_sobre.*

class SobreActivity : AppCompatActivity() {
    private var adapter: SobreAdapter? = null
    private var sobreList: ArrayList<Sobre> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)

        sobreList.add(Sobre ("Kotlin\nAndroid Studio 3.0", R.drawable.iconbuild))
        sobreList.add(Sobre ("Exu Codeiro", R.mipmap.ic_group_work_black_24dp))
        sobreList.add(Sobre ("Pìcasso", R.mipmap.ic_extension_black_24dp))
        sobreList.add(Sobre ("https://github.com/Vitor-Xavier/VeterinarioApp", R.mipmap.ic_code_black_24dp))
        sobreList.add(Sobre ("https://github.com/Vitor-Xavier/VeterinarioAPI", R.mipmap.ic_code_black_24dp))
        adapter = SobreAdapter(sobreList, this)
        listSobre.adapter = adapter
    }
}
