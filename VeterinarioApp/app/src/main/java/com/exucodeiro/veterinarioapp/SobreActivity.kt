package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.exucodeiro.veterinarioapp.Models.Sobre
import com.exucodeiro.veterinarioapp.Util.SobreAdapter
import kotlinx.android.synthetic.main.activity_sobre.*

class SobreActivity : AppCompatActivity() {
    private var adapter: SobreAdapter? = null
    private var sobreList: ArrayList<Sobre> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)

        title = "Sobre"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sobreList.add(Sobre ("Kotlin\nAndroid Studio 3.0", R.mipmap.ic_build_black_24dp))
        sobreList.add(Sobre ("Exu Codeiro", R.mipmap.ic_group_work_black_24dp))
        sobreList.add(Sobre ("Pìcasso", R.mipmap.ic_extension_black_24dp))
        sobreList.add(Sobre ("https://github.com/Vitor-Xavier/VeterinarioApp", R.mipmap.ic_code_black_24dp))
        sobreList.add(Sobre ("https://github.com/Vitor-Xavier/VeterinarioAPI", R.mipmap.ic_code_black_24dp))
        adapter = SobreAdapter(sobreList, this)
        listSobre.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    val it = Intent(this, ItemListActivity::class.java)
                    //startActivity(it)
                    navigateUpTo(Intent(this, ItemListActivity::class.java))
                }
                else -> super.onOptionsItemSelected(item)
            }
}
