package com.exucodeiro.veterinarioapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exucodeiro.veterinarioapp.Models.Animal
import com.exucodeiro.veterinarioapp.Models.TipoAnimal
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.Services.AnimalService
import com.exucodeiro.veterinarioapp.Util.AnimalAdaper
import kotlinx.android.synthetic.main.fragment_animal_list.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.util.*

class AnimalListFragment : Fragment() {
    private var adapter: AnimalAdaper? = null
    private var animais: ArrayList<Animal> = ArrayList()
    private var usuario: Usuario? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        usuario = arguments.getSerializable("usuario") as Usuario

        return inflater!!.inflate(R.layout.fragment_animal_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadData2()
        adapter = AnimalAdaper(animais, activity)
        listAnimais.adapter = adapter
    }

    fun loadData2() {
        val animalService = AnimalService()
        async {
            animais.addAll(animalService.getAnimal(usuario?.usuarioId ?: 0))

            uiThread {
                adapter?.notifyDataSetChanged()
            }
        }
    }
}
