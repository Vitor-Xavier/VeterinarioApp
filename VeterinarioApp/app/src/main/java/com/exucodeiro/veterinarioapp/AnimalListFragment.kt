package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
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
    private lateinit var adapter: AnimalAdaper
    private var animais: ArrayList<Animal> = ArrayList()
    private var usuario: Usuario? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (arguments != null)
            usuario = arguments.getSerializable(ARG_USUARIO) as Usuario?

        adapter = AnimalAdaper(animais, activity)

        return inflater!!.inflate(R.layout.fragment_animal_list, container, false)
    }

    override fun onResume() {
        loadData()
        super.onResume()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listAnimais.adapter = adapter
        listAnimais.setOnCreateContextMenuListener(this)

        fabNovo.setOnClickListener {
            val intAnim = Intent(activity, CadastroAnimalActivity::class.java)
            intAnim.putExtra("animal", Animal(0, "", Date(), "", 1, TipoAnimal(1, "", ""), 1, Usuario(1, "", "", "", "", "", null, ArrayList())))
            startActivity(intAnim)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.clear()
        menu?.add(Menu.NONE, 1, Menu.NONE, "Editar")
        menu?.add(Menu.NONE, 2, Menu.NONE, "Excluir")
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        if (item?.menuInfo == null)
            return false
        var t = item?.menuInfo as AdapterView.AdapterContextMenuInfo

        val animal = adapter.getItem(t.position) as Animal

        when (item.itemId) {
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

    private fun inativaAnimal(usuarioId: Int, animalId: Int) {
        async {
            val animalService = AnimalService()
            animalService.inativaAnimal(usuarioId, animalId)
            loadData()
        }
    }

    private fun loadData() {
        val animalService = AnimalService()
        async {
            animais.clear()
            animais.addAll(animalService.getAnimais(usuario?.usuarioId ?: 0))

            uiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        private val ARG_USUARIO = "usuario"

        fun newInstance(usuario: Usuario): AnimalListFragment {
            val fragment = AnimalListFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARG_USUARIO, usuario)
            fragment.arguments = bundle
            return fragment
        }
    }

}
