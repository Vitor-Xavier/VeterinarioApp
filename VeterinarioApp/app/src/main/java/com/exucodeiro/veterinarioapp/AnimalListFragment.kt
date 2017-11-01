package com.exucodeiro.veterinarioapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exucodeiro.veterinarioapp.Models.Animal
import com.exucodeiro.veterinarioapp.Models.TipoAnimal
import com.exucodeiro.veterinarioapp.Util.AnimalAdaper
import kotlinx.android.synthetic.main.fragment_animal_list.*
import java.util.*

class AnimalListFragment : Fragment() {
    private var adapter: AnimalAdaper? = null
    private var animais: ArrayList<Animal> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_animal_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadData()
        adapter = AnimalAdaper(animais, activity)
        listAnimais.adapter = adapter
    }

    fun loadData() {
        val a1 = Animal(1, "Sr. Ruffus", Calendar.getInstance(), "https://static-cdn.jtvnw.net/jtv_user_pictures/hsdogdog-profile_image-5550ade194780dfc-300x300.jpeg", TipoAnimal(1, "Cachorro"), null)
        animais.add(a1)

        val a2 = Animal(1, "Sr. Ruffus", Calendar.getInstance(), "https://static-cdn.jtvnw.net/jtv_user_pictures/hsdogdog-profile_image-5550ade194780dfc-300x300.jpeg", TipoAnimal(1, "Cachorro"), null)
        animais.add(a2)

        val a3 = Animal(1, "Sr. Ruffus", Calendar.getInstance(), "https://static-cdn.jtvnw.net/jtv_user_pictures/hsdogdog-profile_image-5550ade194780dfc-300x300.jpeg", TipoAnimal(1, "Cachorro"), null)
        animais.add(a3)

        val a4 = Animal(1, "Sr. Ruffus", Calendar.getInstance(), "https://static-cdn.jtvnw.net/jtv_user_pictures/hsdogdog-profile_image-5550ade194780dfc-300x300.jpeg", TipoAnimal(1, "Cachorro"), null)
        animais.add(a4)
    }
}
