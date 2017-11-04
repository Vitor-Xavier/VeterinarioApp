package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.os.Bundle
import android.provider.Contacts
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import com.exucodeiro.veterinarioapp.Models.*
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.exucodeiro.veterinarioapp.Util.ProfissionalAdapter
import kotlinx.android.synthetic.main.fragment_profissional_list.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.net.URL
import kotlin.concurrent.thread

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class ProfissionalListFragment : Fragment() {
    private var adapter: ProfissionalAdapter? = null
    private var profissionais: ArrayList<Profissional> = ArrayList()
    private val profissionalService = ProfissionalService()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_profissional_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadData()

        adapter = ProfissionalAdapter(profissionais, activity)
        listProfissionais.adapter = adapter

        listProfissionais.setOnItemClickListener { parent, view, position, id ->
            val it = Intent(context, ProfissionalDetailActivity::class.java)
            it.putExtra("profissional", (adapter as ProfissionalAdapter).getItem(position) as Profissional)
            startActivity(it)
        }

    }

    fun loadData() {
        async {
            profissionais.clear()
            profissionais.addAll(profissionalService.getProfissionais(-21.1767, -47.8208))

            uiThread {
                adapter?.notifyDataSetChanged()
            }
        }
    }

//    fun loadData() {
//        var contatos = ArrayList<Contato>()
//        contatos.add(Contato(1, "16991796444", true, TipoContato(1, "Telefone")))
//
//        val servicos = ArrayList<Servico>()
//        servicos.add(Servico(1, "Banho", "banho uai", false))
//        val profissional = Profissional(1, "Veterinario", "Teste", "https://cdn2.iconfinder.com/data/icons/medicine-4-1/512/vet-512.png",  "", "123456", Endereco(1, "Rua de Teste", 2010, "5° Andar", "Distrito 2", "14091220", "Ribeirão Preto", "SP", 0.0, 0.0), contatos, servicos)
//        profissionais.add(profissional)
//
//        val profissional2 = Profissional(1, "Veterinario", "Teste", "https://cdn0.iconfinder.com/data/icons/pets-4/92/icon56-12-512.png",  "", "123456", Endereco(1, "Rua de Teste", 2010, "5° Andar", "Distrito 2", "14091220", "Ribeirão Preto", "SP", 0.0, 0.0), contatos, servicos)
//        profissionais.add(profissional2)
//
//        val profissional3 = Profissional(1, "Veterinario", "Teste", "http://www.kibbypark.com/wp-content/uploads/2015/08/wellness-icon.png",  "", "123456", Endereco(1, "Rua de Teste", 2010, "5° Andar", "Distrito 2", "14091220", "Ribeirão Preto", "SP", 0.0, 0.0), contatos, servicos)
//        profissionais.add(profissional3)
//
//        val profissional4 = Profissional(1, "Veterinario", "Teste", "https://cdn0.iconfinder.com/data/icons/profession-2/92/icon40-07-512.png",  "", "123456", Endereco(1, "Rua de Teste", 2010, "5° Andar", "Distrito 2", "14091220", "Ribeirão Preto", "SP", 0.0, 0.0), contatos, servicos)
//        profissionais.add(profissional4)
//
//        val profissional5 = Profissional(1, "Veterinario", "Teste", "http://catsndogs.org/wp-content/uploads/2017/08/vet.png", "", "123456", Endereco(1, "Rua de Teste", 2010, "5° Andar", "Distrito 2", "14091220", "Ribeirão Preto", "SP", 0.0, 0.0), contatos, servicos)
//        profissionais.add(profissional5)
//
//        val profissional6 = Profissional(1, "Veterinario", "Teste", "https://cdn.iconscout.com/public/images/icon/premium/png-512/pet-injection-animal-vet-store-zoo-348ce57a70e3058b-512x512.png",  "", "123456", Endereco(1, "Rua de Teste", 2010, "5° Andar", "Distrito 2", "14091220", "Ribeirão Preto", "SP", 0.0, 0.0), contatos, servicos)
//        profissionais.add(profissional6)
//
//        val profissional7 = Profissional(1, "Veterinario", "Teste", "https://cdn2.iconfinder.com/data/icons/medicine-4-1/512/vet-512.png",  "", "123456", Endereco(1, "Rua de Teste", 2010, "5° Andar", "Distrito 2", "14091220", "Ribeirão Preto", "SP", 0.0, 0.0), contatos, servicos)
//        profissionais.add(profissional7)
//    }

}
