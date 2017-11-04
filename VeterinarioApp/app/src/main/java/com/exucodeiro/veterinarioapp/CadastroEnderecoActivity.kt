package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Endereco
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_endereco.*
import org.jetbrains.anko.toast

class CadastroEnderecoActivity : AppCompatActivity() {
    var cadastro: Profissional? = null
    val profissionalService = ProfissionalService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_endereco)
        title = "Endereço"

        imageIcone.loadUrl("http://www.kibbypark.com/wp-content/uploads/2015/08/wellness-icon.png")

        cadastro = intent.getSerializableExtra("profissional") as Profissional

        buttonProximo.setOnClickListener {
            val endereco = Endereco(0,
                    inputLogradouro.text.toString(),
                    Integer.parseInt(inputNumero.text.toString()),
                    inputComplemento.text.toString(),
                    inputBairro.text.toString(),
                    inputCEP.text.toString(),
                    inputCidade.text.toString(),
                    inputEstado.text.toString(),
                    0.0,
                    0.0)
            cadastro?.endereco = endereco

            if(profissionalService.postProfissional(cadastro as Profissional))
                toast("Registrado")
            else
                toast("Erro")
        }
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }
}
