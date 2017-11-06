package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.exucodeiro.veterinarioapp.Util.MainPageAdapter
import kotlinx.android.synthetic.main.fragment_profissional.*
import android.view.MenuInflater
import kotlinx.android.synthetic.main.toolbar.*


class ProfissionalFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        activity.toolbar.title = "Profissional"
        //toolbar.inflateMenu(R.menu.main_menu)

        return inflater!!.inflate(R.layout.fragment_profissional, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val pageAdapter = MainPageAdapter(childFragmentManager, context)
        viewPager.adapter = pageAdapter
        tabsProfissional.setupWithViewPager(viewPager)

        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.itemProfissional -> {
                val it = Intent(context, CadastroProfissionalActivity::class.java)
                startActivity(it)
            }
            R.id.itemUsuario -> {
                val it = Intent(context, CadastroUsuarioActivity::class.java)
                startActivity(it)
            }
            R.id.itemLogin -> {
                val it = Intent(context, LoginActivity::class.java)
                startActivity(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
