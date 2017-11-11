package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Consulta
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_consulta_detail.*

class ConsultaDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_detail)

        loadData()
    }

    fun loadData() {
        val consulta = intent.getSerializableExtra("consulta") as Consulta

        textAnimalNome.text = consulta.animal.nome
        textDono.text = "${consulta.animal.dono?.nome} ${consulta.animal.dono?.sobrenome}"
        imageAnimal.loadUrl(consulta.animal.imagem)
        imageTipoAnimal.loadUrl(consulta.animal.tipoAnimal.icone)

        textProfissionalNome.text = "${consulta.profissional.nome} ${consulta.profissional.sobrenome}"
        textProfissionalDescricao.text = consulta.profissional.crv
        imageProfissional.loadUrl(consulta.profissional.icone)

        textData.text = consulta.getDataFormatada()
        textDescricao.text = consulta.descricao
        textEndereco.text = consulta.profissional.endereco.toString()
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

}
