package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exucodeiro.veterinarioapp.Models.*
import com.exucodeiro.veterinarioapp.Services.ConsultaService
import com.exucodeiro.veterinarioapp.Services.LoginSettings
import com.exucodeiro.veterinarioapp.Util.ConsultaAdapter
import kotlinx.android.synthetic.main.fragment_consulta.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.util.*

class ConsultaFragment : Fragment() {
    private lateinit var adapter: ConsultaAdapter
    private var consultas: ArrayList<Consulta> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        adapter = ConsultaAdapter(consultas, activity)

        return inflater!!.inflate(R.layout.fragment_consulta, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.title = getString(R.string.consultas)

        listConsultas.adapter = adapter

        listConsultas.setOnItemClickListener { _, _, i, _ ->
            val consulta = adapter.getItem(i) as Consulta
            val it = Intent(activity, ConsultaDetailActivity::class.java)
            it.putExtra("consulta", consulta)
            startActivity(it)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    fun loadData() {
        val settings = LoginSettings(context)
        val login = settings.login

        val consultaService = ConsultaService()
        async {
            consultas.clear()
            if(login.tipo == "Profissional")
                consultas.addAll(consultaService.getConsultasProfissional(login.id))
            else
                consultas.addAll(consultaService.getConsultasUsuario(login.id))

            uiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

}
