package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.exucodeiro.veterinarioapp.Util.MainPageAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Profissionais"

        val pageAdapter = MainPageAdapter(supportFragmentManager, this)
        viewPager.adapter = pageAdapter
        tabsProfissional.setupWithViewPager(viewPager)

        //tabsProfissional.getTabAt(0)?.setIcon(R.mipmap.ic_healing_black_24dp)
        //tabsProfissional.getTabAt(1)?.setIcon(R.mipmap.ic_explore_black_24dp)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                if (intent.getStringExtra("activity") == null) {
                    val it = Intent(this, ItemListActivity::class.java)
                    startActivity(it)

                } else
                    navigateUpTo(Intent(this, ItemListActivity::class.java))
            }
            R.id.itemProfissional -> {
                val it = Intent(this, CadastroProfissionalActivity::class.java)
                startActivity(it)
            }
            R.id.itemUsuario -> {
                val it = Intent(this, CadastroUsuarioActivity::class.java)
                startActivity(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
