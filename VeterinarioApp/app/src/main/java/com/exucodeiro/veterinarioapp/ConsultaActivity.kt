package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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

        title = "Consultas"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadData()
        adapter = ConsultaAdapter(consultas, this)
        listConsultas.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    val it = Intent(this, ItemListActivity::class.java)
                    //startActivity(it)
                    navigateUpTo(Intent(this, ItemListActivity::class.java))
                }
                else -> super.onOptionsItemSelected(item)
            }

    fun loadData() {
        consultas.add(Consulta(1, Calendar.getInstance(), "vai acontecer algo", Animal(1, "", null, "http://pm1.narvii.com/6436/e8e8ab1d82401ddceb169d7d4af167ae753c5735_128.jpg", null, 0, null), Profissional(1, "", "", "https://lh3.googleusercontent.com/-j3PTDPzjqOI/AAAAAAAAAAI/AAAAAAAACpo/S7gaeyPCbzU/s128-c0x00000000-cc-rp-mo-ba2/photo.jpg", "", "123456", 0, null, ArrayList<Contato>(), ArrayList<Servico>())))

        consultas.add(Consulta(1, Calendar.getInstance(), "vai acontecer algo", Animal(1, "", null, "http://pm1.narvii.com/6436/e8e8ab1d82401ddceb169d7d4af167ae753c5735_128.jpg", null, 0, null), Profissional(1, "", "", "https://lh3.googleusercontent.com/-j3PTDPzjqOI/AAAAAAAAAAI/AAAAAAAACpo/S7gaeyPCbzU/s128-c0x00000000-cc-rp-mo-ba2/photo.jpg", "", "123456", 0, null, ArrayList<Contato>(), ArrayList<Servico>())))

        consultas.add(Consulta(1, Calendar.getInstance(), "vai acontecer algo", Animal(1, "", null, "http://pm1.narvii.com/6436/e8e8ab1d82401ddceb169d7d4af167ae753c5735_128.jpg", null, 0, null), Profissional(1, "", "", "https://lh3.googleusercontent.com/-j3PTDPzjqOI/AAAAAAAAAAI/AAAAAAAACpo/S7gaeyPCbzU/s128-c0x00000000-cc-rp-mo-ba2/photo.jpg", "", "123456", 0, null, ArrayList<Contato>(), ArrayList<Servico>())))
    }
}
