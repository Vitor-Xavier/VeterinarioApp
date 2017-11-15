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
import kotlinx.android.synthetic.main.fragment_profissional_page.*


/**
 * A simple [Fragment] subclass.
 */
class ProfissionalPageFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_profissional_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val profissional = (arguments.get("profissional") as Profissional)

        val pageAdapter = ProfissionalPageAdapter(childFragmentManager, context, profissional)
        viewPager.adapter = pageAdapter
        tabsProfissional.setupWithViewPager(viewPager)

        textNome.text = "${profissional.nome} ${profissional.sobrenome}"
        imageBack.loadUrl(profissional.imagem)
        imageIcon.loadUrl(profissional.icone)

        super.onActivityCreated(savedInstanceState)
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

}
