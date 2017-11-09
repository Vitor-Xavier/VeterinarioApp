package com.exucodeiro.veterinarioapp.Util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.TipoAnimal
import com.exucodeiro.veterinarioapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_spinner_item.view.*

/**
 * Created by vitor on 08/11/2017.
 */
class TipoAnimalAdapter(var tipos:List<TipoAnimal>, var activity: Activity) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = View.inflate(activity, R.layout.custom_spinner_item,null)

        view.textTipo.text = tipos[p0].tipo

        view.imageIcone.loadUrl(tipos[p0].icone)

        return view
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null)
            Picasso.with(context).load(url).into(this)
    }

    override fun getItem(p0: Int): Any {
        return tipos[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    fun getById(id: Int) : Int {
        val tipo = tipos.find { it.tipoAnimalId == id }
        return tipos.indexOf(tipo)
    }

    override fun getCount(): Int {
        return tipos.size
    }
}