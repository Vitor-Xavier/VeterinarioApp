package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exucodeiro.veterinarioapp.Models.Profissional
import kotlinx.android.synthetic.main.fragment_profissional_detail.*


class ProfissionalDetailFragment : Fragment() {
    var profissional: Profissional? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_profissional_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        profissional = arguments.getSerializable("profissional") as Profissional

        textDescricao.text = profissional?.nome
        textContatos.text = TextUtils.join(", ", profissional?.contatos)
        textServicos.text = TextUtils.join(", ", profissional?.servicos)

        super.onActivityCreated(savedInstanceState)

        fabConsulta.setOnClickListener {
            val it = Intent(context, CadastroConsultaActivity::class.java)
            it.putExtra("profissional", profissional)
            startActivity(it)
        }
    }

     fun newInstance(profissional: Profissional) : ProfissionalDetailFragment {
        val fragment = ProfissionalDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable("profissional",  profissional)
        fragment.arguments = bundle
        return fragment
    }

}
