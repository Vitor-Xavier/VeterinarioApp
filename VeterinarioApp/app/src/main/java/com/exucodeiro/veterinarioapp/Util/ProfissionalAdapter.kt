package com.exucodeiro.veterinarioapp.Util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_profissional.view.*

/**
 * Created by vitor on 13/10/2017.
 */

data class ProfissionalAdapter(var profissionais:List<Profissional>, var activity: Activity) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = View.inflate(activity, R.layout.item_profissional,null)

        view.textNome.text = "${profissionais[p0].nome} ${profissionais.get(p0).sobrenome}"
        view.textEndereco.text = profissionais[p0].endereco.toString()
        view.imageProfissional.loadUrl(profissionais[p0].icone)

        if(profissionais[p0].online)
            view.imageAtivo.loadUrl(R.mipmap.online_icon)
        return view
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }

    fun ImageView.loadUrl(draw: Int) {
        if (draw != null)
            Picasso.with(context).load(draw).into(this)
    }

    override fun getItem(p0: Int): Any {
        return profissionais[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return profissionais.size
    }

}