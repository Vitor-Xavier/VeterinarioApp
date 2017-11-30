package com.exucodeiro.veterinarioapp

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        adapter = ProfissionalAdapter(profissionais, activity)
        profissionais.addAll(arguments.getSerializable(ARG_PROFISSIONAL) as ArrayList<Profissional>)

        return inflater!!.inflate(R.layout.fragment_profissional_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listProfissionais.adapter = adapter

        listProfissionais.setOnItemClickListener { _, _, position, _ ->
            val intentPro = Intent(context, ProfissionalDetailActivity::class.java)
            intentPro.putExtra("profissional", adapter.getItem(position) as Profissional)
            startActivity(intentPro)
        }
    }

    companion object {
        private val ARG_PROFISSIONAL = "profissionais"

        fun newInstance(profissionais: ArrayList<Profissional>): ProfissionalListFragment {
            val fragment = ProfissionalListFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARG_PROFISSIONAL, profissionais)
            fragment.arguments = bundle
            return fragment
        }
    }

}
