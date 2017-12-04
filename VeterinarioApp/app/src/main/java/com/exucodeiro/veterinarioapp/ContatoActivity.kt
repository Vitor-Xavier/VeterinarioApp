package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Contato
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.Services.ContatoService
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.exucodeiro.veterinarioapp.Services.UsuarioService
import com.exucodeiro.veterinarioapp.Util.ContatoAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_contato.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class ContatoActivity : AppCompatActivity() {
    private lateinit var contatoAdapter: ContatoAdapter
    private var contatos: ArrayList<Contato> = ArrayList()
    private var usuario: Usuario? = null
    private var profissional: Profissional? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)
        textTipo.text = getString(R.string.contatos)

        contatoAdapter = ContatoAdapter(contatos, this)
        listContatos.adapter = contatoAdapter

        listContatos.setOnCreateContextMenuListener(this)

        imageIcone.setOnClickListener {
            val intentStart = Intent(this@ContatoActivity, MainActivity::class.java)
            intentStart.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentStart)
        }

        buttonAdicionar.setOnClickListener {
            val intentContato = Intent(this, CadastroContatoActivity::class.java)
            if (profissional != null)
                intentContato.putExtra("profissional", profissional)
            else
                intentContato.putExtra("usuario", usuario)
            startActivity(intentContato)
        }
    }

    override fun onResume() {
        super.onResume()

        if (intent.getSerializableExtra("usuario") != null)
            loadUsuario()
        else
            loadProfissional()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.add(Menu.NONE, 1, Menu.NONE, "Editar")
        menu?.add(Menu.NONE, 2, Menu.NONE, "Excluir")
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val t = item?.menuInfo as AdapterView.AdapterContextMenuInfo

        val contato = contatoAdapter.getItem(t.position) as Contato

        when (item.itemId) {
            1 -> {
                val intentContato = Intent(this, CadastroContatoActivity::class.java)
                if (profissional != null)
                    intentContato.putExtra("profissional", profissional)
                else
                    intentContato.putExtra("usuario", usuario)
                intentContato.putExtra("contato", contato)
                startActivity(intentContato)
            }
            2 -> {
                inativaContato(contato.contatoId)
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intentStart = Intent(this,  MainActivity::class.java)
        intentStart.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intentStart.putExtra("update", true)
        startActivity(intentStart)
    }

    private fun inativaContato(contatoId: Int) {
        async {
            val contatoService = ContatoService()
            contatoService.inativaContato(contatoId)

            if (profissional != null)
                loadProfissional()
            else
                loadUsuario()
        }
    }

    private fun loadUsuario() {
        usuario = intent.getSerializableExtra("usuario") as Usuario?

        async {
            val usuarioService = UsuarioService()
            usuario = usuarioService.getUsuario(usuario?.usuarioId ?: 0)

            if (usuario != null) {
                contatos.clear()
                contatos.addAll(usuario?.contatos ?: ArrayList())

                uiThread {
                    textNome.text = "${usuario?.nome} ${usuario?.sobrenome}"
                    imageIcone.loadUrl(usuario?.imagem)

                    contatoAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun ImageView.loadUrl(url: String?) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }

    private fun loadProfissional() {
        profissional = intent.getSerializableExtra("profissional") as Profissional?

        async {
            val profissionalService = ProfissionalService()
            profissional = profissionalService.getProfissional(profissional?.profissionalId ?: 0)

            if (profissional != null) {
                contatos.clear()
                contatos.addAll(profissional?.contatos ?: ArrayList())

                uiThread {
                    textNome.text = "${profissional?.nome} ${profissional?.sobrenome}"
                    imageIcone.loadUrl(profissional?.icone)

                    contatoAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}
