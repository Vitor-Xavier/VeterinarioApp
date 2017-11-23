package com.exucodeiro.veterinarioapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Util.ProfissionalPageAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profissional_perfil.*

class ProfissionalPerfilFragment : Fragment() {
    private var profissional: Profissional? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null)
            profissional = (arguments.getSerializable("profissional") as Profissional)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_profissional_perfil, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.title = getString(R.string.profissional)

        if (profissional != null) {
            val pageAdapter = ProfissionalPageAdapter(childFragmentManager, profissional as Profissional)
            viewPager.adapter = pageAdapter
            tabsProfissional.setupWithViewPager(viewPager)

            textNome.text = "${profissional?.nome} ${profissional?.sobrenome}"
            imageBack.loadUrl(profissional?.imagem)
            imageIcon.loadUrl(profissional?.icone)
        }
    }

    fun ImageView.loadUrl(url: String?) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }

    companion object {
        private val ARG_PROFISSIONAL = "profissional"

        fun newInstance(profissional: Profissional): ProfissionalPerfilFragment {
            val fragment = ProfissionalPerfilFragment()
            val args = Bundle()
            args.putSerializable(ARG_PROFISSIONAL, profissional)
            fragment.arguments = args
            return fragment
        }
    }

}
