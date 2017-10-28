package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.exucodeiro.veterinarioapp.Models.*
import com.exucodeiro.veterinarioapp.Util.MainPageAdapter
import com.exucodeiro.veterinarioapp.Util.ProfissionalAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var adapter: ProfissionalAdapter? = null
    private var profissionais: ArrayList<Profissional> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pageAdapter = MainPageAdapter(supportFragmentManager, this)
        viewPager.adapter = pageAdapter
        tabsProfissional.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.itemConsultas) {
            val it = Intent(this, ConsultaActivity::class.java)
            startActivity(it)
        }
        if (item?.itemId == R.id.itemUsuario) {
            //val it = Intent(this, UsuarioDetailActivity::class.java)
            val it = Intent(this, ProfissionalDetailActivity::class.java)
            startActivity(it)
        }
        if (item?.itemId == R.id.itemCadastro) {
            val it = Intent(this, CadastroProfissionalActivity::class.java)
            startActivity(it)
        }
        return super.onOptionsItemSelected(item)
    }

}
