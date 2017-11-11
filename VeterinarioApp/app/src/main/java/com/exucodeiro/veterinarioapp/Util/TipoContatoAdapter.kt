package com.exucodeiro.veterinarioapp.Util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.TipoContato
import com.exucodeiro.veterinarioapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_tipo_contato.view.*

/**
 * Created by vitor on 10/11/2017.
 */
class TipoContatoAdapter(var tipos:List<TipoContato>, var activity: Activity) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = View.inflate(activity, R.layout.item_tipo_contato,null)

        view.textTipo.text = tipos[p0].nome

        view.imageIcone.loadUrl(tipos[p0].icone)

        return view
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

    override fun getItem(p0: Int): Any {
        return tipos[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return tipos.size
    }
}