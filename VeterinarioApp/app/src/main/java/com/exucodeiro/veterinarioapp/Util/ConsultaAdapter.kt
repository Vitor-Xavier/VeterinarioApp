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

data class ConsultaAdapter(private var consultas:List<Consulta>, var activity: Activity) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = View.inflate(activity, R.layout.item_consulta,null)

        view.textData.text = consultas[p0].getDataFormatada()
        view.textProfissional.text = "${consultas[p0].profissional.nome} ${consultas[p0].profissional.sobrenome}"
        view.textAnimal.text = consultas[p0].animal.nome
        view.imageProfissional.loadUrl(consultas[p0].profissional.icone)
        view.imageAnimal.loadUrl(consultas[p0].animal.imagem)

        when (consultas[p0].status) {
            0 -> view.imageStatus.loadUrl(R.mipmap.ic_event_black_24dp)
            1 -> view.imageStatus.loadUrl(R.mipmap.ic_event_available_black_24dp)
            2 -> view.imageStatus.loadUrl(R.mipmap.ic_event_busy_black_24dp)
            3 -> view.imageStatus.loadUrl(R.mipmap.ic_event_note_black_24dp)
        }

        return view
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }

    fun ImageView.loadUrl(draw: Int) {
        Picasso.with(context).load(draw).into(this)
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