package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Util.ProfissionalPageAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profissional_detail.*

class ProfissionalDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profissional_detail)

        val profissional = (intent.getSerializableExtra("profissional") as Profissional)

        val pageAdapter = ProfissionalPageAdapter(supportFragmentManager, this, profissional)
        viewPager.adapter = pageAdapter
        tabsProfissional.setupWithViewPager(viewPager)

        textNome.text = "${profissional.nome} ${profissional.sobrenome}"
        imageBack.loadUrl(profissional.imagem)
        imageIcon.loadUrl(profissional.icone)
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }
}
