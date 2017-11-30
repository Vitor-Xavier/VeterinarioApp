package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Models.Servico
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.exucodeiro.veterinarioapp.Services.ServicoService
import com.exucodeiro.veterinarioapp.Util.ServicoAdapter
import kotlinx.android.synthetic.main.activity_cadastro_servico.*
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class CadastroServicoActivity : AppCompatActivity() {
    private lateinit var servicoAdapter: ServicoAdapter
    private val servicos = ArrayList<Servico>()
    private var profissional: Profissional? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_servico)

        profissional = intent.getSerializableExtra("profissional") as Profissional?

        servicoAdapter = ServicoAdapter(servicos, this)
        listServicos.adapter = servicoAdapter

        listServicos.setOnItemClickListener { adapterView, view, i, l ->
            async {
                val servico = servicoAdapter.getItem(i) as Servico
                val profissionalService = ProfissionalService()

                if (profissionalService.adicionaServico(profissional?.profissionalId ?: 0, servico))
                    uiThread {
                        toast("${servico.nome} adicionado")
                    }
                else
                    uiThread {
                        toast("Serviço não pode ser adicionado")
                    }
            }
        }

        loadData()
    }

    private fun loadData() {
        async {
            val servicoService = ServicoService()
            servicos.addAll(servicoService.getServicos())

            uiThread {
                servicoAdapter.notifyDataSetChanged()
            }
        }
    }
}
