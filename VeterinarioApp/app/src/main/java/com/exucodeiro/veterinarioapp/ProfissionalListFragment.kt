package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exucodeiro.veterinarioapp.Models.*
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.exucodeiro.veterinarioapp.Util.ProfissionalAdapter
import kotlinx.android.synthetic.main.fragment_profissional_list.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class ProfissionalListFragment : Fragment() {
    private lateinit var adapter: ProfissionalAdapter
    private var profissionais: ArrayList<Profissional> = ArrayList()
    private val profissionalService = ProfissionalService()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        adapter = ProfissionalAdapter(profissionais, activity)
        loadData()

        return inflater!!.inflate(R.layout.fragment_profissional_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listProfissionais.adapter = adapter

        listProfissionais.setOnItemClickListener { _, _, position, _ ->
            val it = Intent(context, ProfissionalDetailActivity::class.java)
            it.putExtra("profissional", adapter.getItem(position) as Profissional)
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

}
