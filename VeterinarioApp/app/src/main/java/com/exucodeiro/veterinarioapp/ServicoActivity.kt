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
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Models.Servico
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.exucodeiro.veterinarioapp.Services.ServicoService
import com.exucodeiro.veterinarioapp.Util.ServicoAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_servico.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class ServicoActivity : AppCompatActivity() {
    private lateinit var servicoAdapter: ServicoAdapter
    private var servicos: ArrayList<Servico> = ArrayList()
    private var profissional: Profissional? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servico)
        textTipo.text = getString(R.string.servicos)

        servicoAdapter = ServicoAdapter(servicos, this)
        listServicos.adapter = servicoAdapter

        listServicos.setOnCreateContextMenuListener(this)

        buttonAdicionar.setOnClickListener {
            val intentServico = Intent(this, CadastroServicoActivity::class.java)
            intentServico.putExtra("profissional", profissional)
            startActivity(intentServico)
        }
    }

    override fun onResume() {
        super.onResume()

        loadProfissional()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intentStart = Intent(this,  MainActivity::class.java)
        intentStart.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intentStart.putExtra("update", true)
        startActivity(intentStart)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.clear()
        menu?.add(Menu.NONE, 1, Menu.NONE, "Excluir")
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        var t = item?.menuInfo as AdapterView.AdapterContextMenuInfo

        val servico = servicoAdapter.getItem(t.position) as Servico

        when (item.itemId) {
            1 -> {
                inativaServico(servico.servicoId)
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun inativaServico(servicoId: Int) {
        async {
            val servicoService = ServicoService()
            servicoService.inativaServico(profissional?.profissionalId ?: 0, servicoId)

            if (profissional != null)
                loadProfissional()
        }
    }

    private fun loadProfissional() {
        profissional = intent.getSerializableExtra("profissional") as Profissional?

        async {
            val profissionalService = ProfissionalService()
            profissional = profissionalService.getProfissional(profissional?.profissionalId ?: 0)

            if (profissional != null) {
                servicos.clear()
                servicos.addAll(profissional?.servicos ?: ArrayList())

                uiThread {
                    textNome.text = "${profissional?.nome} ${profissional?.sobrenome}"
                    imageIcone.loadUrl(profissional?.icone)

                    servicoAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun ImageView.loadUrl(url: String?) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }

}
