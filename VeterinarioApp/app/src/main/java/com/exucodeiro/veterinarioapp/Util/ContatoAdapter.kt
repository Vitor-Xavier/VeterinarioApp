package com.exucodeiro.veterinarioapp.Util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Contato
import com.exucodeiro.veterinarioapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_contato.view.*

data class ContatoAdapter(private var contatos: List<Contato>, var activity: Activity) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = View.inflate(activity, R.layout.item_contato,null)

        view.textContato.text = contatos[p0].texto
        view.imageIcone.loadUrl(contatos[p0].tipoContato.icone)

        return view
    }

    fun ImageView.loadUrl(url: String?) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }

    fun ImageView.loadUrl(draw: Int) {
        Picasso.with(context).load(draw).into(this)
    }

    override fun getItem(p0: Int): Any {
        return contatos[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return contatos.size
    }

}