package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Animal
import com.exucodeiro.veterinarioapp.Models.TipoAnimal
import com.exucodeiro.veterinarioapp.Util.AnimalAdaper
import com.exucodeiro.veterinarioapp.Util.UsuarioPageAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_usuario_detail.*
import java.util.*

class UsuarioDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_detail)

        val pageAdapter = UsuarioPageAdapter(supportFragmentManager, this)
        viewPager.adapter = pageAdapter
        tabsUsuario.setupWithViewPager(viewPager)

        imageIcon.loadUrl("https://cdn2.iconfinder.com/data/icons/medicine-4-1/512/vet-512.png")

        imageBack.loadUrl("https://i.ytimg.com/vi/hdxKJsTvvxQ/maxresdefault.jpg")
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

}
