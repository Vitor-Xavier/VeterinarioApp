package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import com.exucodeiro.veterinarioapp.Models.Animal
import com.exucodeiro.veterinarioapp.Models.TipoAnimal
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.Services.AnimalService
import com.exucodeiro.veterinarioapp.Util.AnimalAdaper
import kotlinx.android.synthetic.main.fragment_animal_list.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.util.*
import kotlin.collections.ArrayList

class AnimalListFragment : Fragment() {
    private var adapter: AnimalAdaper? = null
    private var animais: ArrayList<Animal> = ArrayList()
    private var usuario: Usuario? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        usuario = arguments.getSerializable("usuario") as Usuario

        return inflater!!.inflate(R.layout.fragment_animal_list, container, false)
    }

    override fun onResume() {
        loadData()
        super.onResume()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        adapter = AnimalAdaper(animais, activity)
        listAnimais.adapter = adapter
        listAnimais.setOnCreateContextMenuListener(this)

        fabNovo.setOnClickListener {
            val it = Intent(activity, CadastroAnimalActivity::class.java)
            it.putExtra("animal", Animal(0, "", Date(), "", 1, TipoAnimal(1, "", ""), 1, Usuario(1, "", "", "", null, ArrayList())))
            startActivity(it)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.add(Menu.NONE, 1, Menu.NONE, "Editar")
        menu?.add(Menu.NONE, 2, Menu.NONE, "Excluir")
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        var t = item?.menuInfo as AdapterView.AdapterContextMenuInfo

        val animal = adapter?.getItem(t.position) as Animal

        when (item?.itemId) {
            1 -> {
                val it = Intent(context, CadastroAnimalActivity::class.java)
                it.putExtra("animal", animal)
                startActivity(it)
            }
            2 -> {
                inativaAnimal(usuario?.usuarioId ?: 0, animal.animalId)
            }
        }

        return super.onContextItemSelected(item)
    }

    fun inativaAnimal(usuarioId: Int, animalId: Int) {
        async {
            val animalService = AnimalService()
            animalService.inativaAnimal(usuarioId, animalId)
            loadData()
        }

    }

    fun loadData() {
        val animalService = AnimalService()
        async {
            animais.clear()
            animais.addAll(animalService.getAnimal(usuario?.usuarioId ?: 0))

            uiThread {
                adapter?.notifyDataSetChanged()
            }
        }
    }
}
