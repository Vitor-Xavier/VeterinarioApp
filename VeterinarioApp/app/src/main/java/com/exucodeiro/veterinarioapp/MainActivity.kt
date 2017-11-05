package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.exucodeiro.veterinarioapp.Util.MainPageAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(detail_toolbar)

        setSupportActionBar(detail_toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Profissionais"

        val pageAdapter = MainPageAdapter(supportFragmentManager, this)
        viewPager.adapter = pageAdapter
        tabsProfissional.setupWithViewPager(viewPager)

        //tabsProfissional.getTabAt(0)?.setIcon(R.mipmap.ic_healing_black_24dp)
        //tabsProfissional.getTabAt(1)?.setIcon(R.mipmap.ic_explore_black_24dp)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, detail_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    //cria menu Esquerda ...
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //opções do menu Esquerda ...
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
            R.id.itemLogin -> {
                val it = Intent(this, LoginActivity::class.java)
                startActivity(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_inicio -> {
                val it = Intent(this, MainActivity::class.java)
                startActivity(it)
            }
            R.id.nav_perfil -> {
                val it = Intent(this, UsuarioDetailActivity::class.java)
                startActivity(it)
            }
            R.id.nav_agenda -> {
                val it = Intent(this, ConsultaActivity::class.java)
                startActivity(it)
            }
            R.id.nav_info -> {
                val it = Intent(this, SobreActivity::class.java)
                startActivity(it)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}


