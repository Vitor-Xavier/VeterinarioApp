package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.profissionais)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        val fragment = ProfissionalFragment()
        supportFragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_inicio -> {
                val fragment = ProfissionalFragment()
                supportFragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit()
            }
            R.id.nav_perfil -> {
                val fragment = UsuarioFragment()
                supportFragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit()
            }
            R.id.nav_agenda -> {
                val fragment = ConsultaFragment()
                supportFragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit()
            }
            R.id.nav_info -> {
                val fragment = SobreFragment()
                supportFragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}


