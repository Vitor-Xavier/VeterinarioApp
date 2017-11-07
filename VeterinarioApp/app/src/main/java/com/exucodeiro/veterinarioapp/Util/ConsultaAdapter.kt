package com.exucodeiro.veterinarioapp.Util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Consulta
import com.exucodeiro.veterinarioapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_consulta.view.*

/**
 * Created by vitor on 23/10/2017.
 */

data class ConsultaAdapter(var consultas:List<Consulta>, var activity: Activity) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = View.inflate(activity, R.layout.item_consulta,null)

        view.textData.text = consultas[p0].getData()
        view.textProfissional.text = "${consultas[p0].profissional.nome} ${consultas[p0].profissional.sobrenome}"
        view.textAnimal.text = consultas[p0].animal.nome
        view.imageProfissional.loadUrl(consultas[p0].profissional.icone)
        view.imageAnimal.loadUrl(consultas[p0].animal.imagem)

        return view
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

    override fun getItem(p0: Int): Any {
        return consultas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return consultas.size
    }

}