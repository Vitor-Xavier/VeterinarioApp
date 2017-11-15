package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.Services.LoginService
import com.exucodeiro.veterinarioapp.Services.LoginSettings
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.exucodeiro.veterinarioapp.Services.UsuarioService
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var profissional: Profissional? = null
    var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.profissionais)
        setSupportActionBar(toolbar)
        val objectMapper = jacksonObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        val fragment = ProfissionalFragment()
        supportFragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit()

        loadData()
    }

    fun loadData() {
        async {
            val settings = LoginSettings(baseContext)
            val loginService = LoginService()
            val login = loginService.logar(settings.login.nomeUsuario, settings.login.senha)
            if (login == null || login.id == 0) {
                nav_view.menu.removeItem(R.id.nav_perfil)
                nav_view.menu.removeItem(R.id.nav_agenda)
            } else {
                if (settings.login.tipo == "Profissional") {
                    val profissionalService = ProfissionalService()
                    profissional = profissionalService.getProfissional(settings.login.id)

                    uiThread {
                        nav_view.getHeaderView(0).textNome.text = profissional?.nome
                        nav_view.getHeaderView(0).textView.text = "Profissional"

                        nav_view.getHeaderView(0).imageView.loadUrl(profissional?.icone ?: "")
                    }
                } else {
                    val usuarioService = UsuarioService()
                    usuario = usuarioService.getUsuario(settings.login.id)

                    uiThread {
                        nav_view.getHeaderView(0).textNome.text = usuario?.nome
                        nav_view.getHeaderView(0).textView.text = "Usuário"

                        nav_view.getHeaderView(0).imageView.loadUrl(usuario?.imagem ?: "")
                    }
                }
            }
        }
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_inicio -> {
                val fragment = ProfissionalFragment()
                supportFragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit()
            }
            R.id.nav_perfil -> {
                val settings = LoginSettings(this)
                if (settings.login.tipo == "Profissional") {
                    val fragmentPro = ProfissionalPageFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("profissional", profissional)
                    fragmentPro.arguments = bundle
                    supportFragmentManager.beginTransaction().replace(R.id.content_view, fragmentPro).commit()
                } else {
                    val fragment = UsuarioFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("usuario", usuario)
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit()
                }
            }
            R.id.nav_agenda -> {
                val fragment = ConsultaFragment()
                supportFragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit()
            }
            R.id.nav_info -> {
                val fragment = SobreFragment()
                supportFragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit()
            }
            R.id.nav_sair -> {
                val settings = LoginSettings(this)
                settings.reset()

                finish()
                startActivity(intent)
                //supportFragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}


