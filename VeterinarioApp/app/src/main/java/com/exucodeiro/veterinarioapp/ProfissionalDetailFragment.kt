package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Services.LoginSettings
import kotlinx.android.synthetic.main.fragment_profissional_detail.*

class ProfissionalDetailFragment : Fragment() {
    private var profissional: Profissional? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (arguments != null)
            profissional = arguments.getSerializable(ARG_PROFISSIONAL) as Profissional

        return inflater!!.inflate(R.layout.fragment_profissional_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadData()

        fabConsulta.setOnClickListener {
            val cadIntent = Intent(context, CadastroConsultaActivity::class.java)
            cadIntent.putExtra("profissional", profissional)
            startActivity(cadIntent)
        }
    }



    private fun loadData() {
        textDescricao.text = profissional?.descricao
        textContatos.text = if (profissional?.contatos?.isEmpty() != true)
            TextUtils.join(", ", profissional?.contatos) else "Sem contatos registrados"
        textServicos.text = if (profissional?.servicos?.isEmpty() != true)
            TextUtils.join(", ", profissional?.servicos) else "Sem serviços registrados"

        val loginSetting = LoginSettings(context)
        if (loginSetting.login.tipo == "Profissional" || loginSetting.login.id == 0)
            fabConsulta.visibility = View.GONE
    }

    companion object {
        private val ARG_PROFISSIONAL = "profissional"

        fun newInstance(profissional: Profissional): ProfissionalDetailFragment {
            val fragment = ProfissionalDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARG_PROFISSIONAL, profissional)
            fragment.arguments = bundle
            return fragment
        }
    }
}
