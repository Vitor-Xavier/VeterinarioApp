package com.exucodeiro.veterinarioapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exucodeiro.veterinarioapp.Models.*
import com.exucodeiro.veterinarioapp.Util.ConsultaAdapter
import kotlinx.android.synthetic.main.fragment_consulta.*
import java.util.*

class ConsultaFragment : Fragment() {
    private var adapter: ConsultaAdapter? = null
    private var consultas: ArrayList<Consulta> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_consulta, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        activity.title = getString(R.string.consultas)

        loadData()
        adapter = ConsultaAdapter(consultas, activity)
        listConsultas.adapter = adapter

        super.onActivityCreated(savedInstanceState)
    }

    fun loadData() {
        consultas.add(Consulta(1, Calendar.getInstance(), "vai acontecer algo", Animal(1, "Dog chateado", null, "http://pm1.narvii.com/6436/e8e8ab1d82401ddceb169d7d4af167ae753c5735_128.jpg",  1, TipoAnimal(1, "Cachorro"), 0, null), Profissional(1, "Veterinaria", "Teste", "https://lh3.googleusercontent.com/-j3PTDPzjqOI/AAAAAAAAAAI/AAAAAAAACpo/S7gaeyPCbzU/s128-c0x00000000-cc-rp-mo-ba2/photo.jpg", "https://lh3.googleusercontent.com/-j3PTDPzjqOI/AAAAAAAAAAI/AAAAAAAACpo/S7gaeyPCbzU/s128-c0x00000000-cc-rp-mo-ba2/photo.jpg", "123456", 0, null, ArrayList<Contato>(), ArrayList<Servico>())))

        consultas.add(Consulta(1, Calendar.getInstance(), "vai acontecer algo", Animal(1, "Dog chateado", null, "http://pm1.narvii.com/6436/e8e8ab1d82401ddceb169d7d4af167ae753c5735_128.jpg",  1, TipoAnimal(1, "Cachorro"), 0, null), Profissional(1, "Veterinaria", "Teste", "https://lh3.googleusercontent.com/-j3PTDPzjqOI/AAAAAAAAAAI/AAAAAAAACpo/S7gaeyPCbzU/s128-c0x00000000-cc-rp-mo-ba2/photo.jpg", "https://lh3.googleusercontent.com/-j3PTDPzjqOI/AAAAAAAAAAI/AAAAAAAACpo/S7gaeyPCbzU/s128-c0x00000000-cc-rp-mo-ba2/photo.jpg", "123456", 0, null, ArrayList<Contato>(), ArrayList<Servico>())))

        consultas.add(Consulta(1, Calendar.getInstance(), "vai acontecer algo", Animal(1, "Dog chateado", null, "http://pm1.narvii.com/6436/e8e8ab1d82401ddceb169d7d4af167ae753c5735_128.jpg",  1, TipoAnimal(1, "Cachorro"), 0, null), Profissional(1, "Veterinaria", "Teste", "https://lh3.googleusercontent.com/-j3PTDPzjqOI/AAAAAAAAAAI/AAAAAAAACpo/S7gaeyPCbzU/s128-c0x00000000-cc-rp-mo-ba2/photo.jpg", "https://lh3.googleusercontent.com/-j3PTDPzjqOI/AAAAAAAAAAI/AAAAAAAACpo/S7gaeyPCbzU/s128-c0x00000000-cc-rp-mo-ba2/photo.jpg", "123456", 0, null, ArrayList<Contato>(), ArrayList<Servico>())))
    }

}
