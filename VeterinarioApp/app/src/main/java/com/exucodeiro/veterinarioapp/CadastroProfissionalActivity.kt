package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_profissional.*

class CadastroProfissionalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_profissional)
        title = "Profissional"

        imageBackground.loadUrl("http://decoclinic.com/wp-content/uploads/2016/11/camara2-Tartessos.jpg")

        imageIcone.loadUrl("http://www.kibbypark.com/wp-content/uploads/2015/08/wellness-icon.png")

        buttonProximo.setOnClickListener {
            val it = Intent(this, CadastroEnderecoActivity::class.java)
            startActivity(it)
        }
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }


}