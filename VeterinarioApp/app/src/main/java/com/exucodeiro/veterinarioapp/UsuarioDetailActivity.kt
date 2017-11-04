package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.exucodeiro.veterinarioapp.Models.*
import com.exucodeiro.veterinarioapp.Util.AnimalAdaper
import com.exucodeiro.veterinarioapp.Util.UsuarioPageAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_usuario_detail.*
import kotlinx.android.synthetic.main.custom_tab.view.*
import java.util.*
import kotlin.collections.ArrayList

class UsuarioDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = Usuario(1, "Nome", "usuário", "https://cdn2.iconfinder.com/data/icons/medicine-4-1/512/vet-512.png", Endereco(1, "Rua teste 01", 100, "Bloco 2", "Jardim Algo", "1234585", "Ribeirão Preto", "SP", -21.89, -47.23), ArrayList<Contato>())

        val pageAdapter = UsuarioPageAdapter(supportFragmentManager, this, user)
        viewPager.adapter = pageAdapter
        tabsUsuario.setupWithViewPager(viewPager)

        val tab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null)
        tab.tabContent.text = "  DETALHES"
        tab.tabContent.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_assignment_black_24dp, 0, 0, 0)

        val tab2 = LayoutInflater.from(this).inflate(R.layout.custom_tab, null)
        tab2.tabContent.text = "  PETS"
        tab2.tabContent.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_pets_black_24dp, 0, 0, 0)

        tabsUsuario.getTabAt(0)?.customView = tab
        //tabsUsuario.getTabAt(0)?.setIcon(R.mipmap.ic_assignment_black_24dp)
        //tabsUsuario.getTabAt(1)?.setIcon(R.mipmap.ic_pets_black_24dp)
        tabsUsuario.getTabAt(1)?.customView = tab2

        imageIcon.loadUrl(user.imagem)

        imageBack.loadUrl("https://i.ytimg.com/vi/hdxKJsTvvxQ/maxresdefault.jpg")
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    //val it = Intent(this, ItemListActivity::class.java)
                    //startActivity(it)
                    navigateUpTo(Intent(this, ItemListActivity::class.java))
                }
                else -> super.onOptionsItemSelected(item)
            }

    fun ImageView.loadUrl(url: String) {
        if (!url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

}
