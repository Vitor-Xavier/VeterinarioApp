package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Contato
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Models.TipoContato
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.Services.ContatoService
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.exucodeiro.veterinarioapp.Services.TipoContatoService
import com.exucodeiro.veterinarioapp.Services.UsuarioService
import com.exucodeiro.veterinarioapp.Util.TipoContatoAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_contato.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class CadastroContatoActivity : AppCompatActivity(), View.OnFocusChangeListener {
    private lateinit var tipoAdapter: TipoContatoAdapter
    private val tiposContato = ArrayList<TipoContato>()
    private var profissional: Profissional? = null
    private var usuario: Usuario? = null
    private var contato: Contato? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_contato)

        tipoAdapter = TipoContatoAdapter(tiposContato, this)
        spinnerTipoContato.adapter = tipoAdapter
        loadTipos()

        loadData()

        buttonProximo.setOnClickListener {
            if (!validate())
                return@setOnClickListener

            fillContato()
            async {
                if (contato?.contatoId != 0) {
                    val contatoService = ContatoService()
                    contatoService.atualizaContato(contato as Contato)
                } else {
                    if (profissional != null) {
                        val profissionalService = ProfissionalService()
                        profissionalService.adicionaContato(profissional?.profissionalId ?: 0, contato as Contato)
                    } else {
                        val usuarioService = UsuarioService()
                        usuarioService.adicionaContato(usuario?.usuarioId ?: 0, contato as Contato)
                    }
                }

                uiThread {
                    finish()
                }
            }
        }
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        (p0 as EditText)
        if (p0.text.toString().trim() == "" && !p1)
            p0.error = "Conteudo inválido"
    }

    private fun validate() : Boolean {
        if (inputTexto.text.toString().trim() == "") {
            inputTexto.requestFocus()
            inputTexto.error = "Informe o contato"
            return false
        }
        if (spinnerTipoContato.selectedItemPosition < 0) {
            spinnerTipoContato.requestFocus()
            return false
        }
        return true
    }

    private fun fillContato() {
        val tipoContato = tipoAdapter.getItem(spinnerTipoContato.selectedItemPosition) as TipoContato

        contato = Contato(contato?.contatoId ?: 0,
                inputTexto.text.toString(),
                false,
                tipoContato.tipoContatoId,
                tipoContato)
    }

    private fun loadData() {
        contato = intent.getSerializableExtra("contato") as Contato?

        if (contato != null) {
            inputTexto.setText(contato?.texto)
            spinnerTipoContato.setSelection(tipoAdapter.getById(contato?.tipoContatoId ?: 0))
        }

        if (intent.getSerializableExtra("profissional") != null) {
            profissional = intent.getSerializableExtra("profissional") as Profissional

            textNome.text = "${profissional?.nome} ${profissional?.sobrenome}"
            imageIcone.loadUrl(profissional?.icone)
        } else {
            usuario = intent.getSerializableExtra("usuario") as Usuario

            textNome.text = "${usuario?.nome} ${usuario?.sobrenome}"
            imageIcone.loadUrl(usuario?.imagem)
        }
    }

    private fun loadTipos() {
        async {
            val tipoContatoService = TipoContatoService()

            tiposContato.addAll(tipoContatoService.getTiposContato())

            uiThread {
                tipoAdapter.notifyDataSetChanged()
            }
        }
    }

    fun ImageView.loadUrl(url: String?) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }
}
