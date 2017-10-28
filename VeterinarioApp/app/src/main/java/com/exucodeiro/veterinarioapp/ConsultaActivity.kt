package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.exucodeiro.veterinarioapp.Models.*
import com.exucodeiro.veterinarioapp.Util.ConsultaAdapter
import kotlinx.android.synthetic.main.activity_consulta.*
import java.util.*
import kotlin.collections.ArrayList

class ConsultaActivity : AppCompatActivity() {
    private var adapter: ConsultaAdapter? = null
    private var consultas: ArrayList<Consulta> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta)

        loadData()
        adapter = ConsultaAdapter(consultas, this)
        listConsultas.adapter = adapter
    }

    fun loadData() {
        consultas.add(Consulta(1, Calendar.getInstance(), "vai acontecer algo", Animal(1, "", null, "http://pm1.narvii.com/6436/e8e8ab1d82401ddceb169d7d4af167ae753c5735_128.jpg", null, null), Profissional(1, "", "", "https://lh3.googleusercontent.com/-j3PTDPzjqOI/AAAAAAAAAAI/AAAAAAAACpo/S7gaeyPCbzU/s128-c0x00000000-cc-rp-mo-ba2/photo.jpg", "123456", null, ArrayList<Contato>(), ArrayList<Servico>(), true)))
    }
}
