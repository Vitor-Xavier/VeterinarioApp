package com.exucodeiro.veterinarioapp.Util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.exucodeiro.veterinarioapp.Models.Servico
import com.exucodeiro.veterinarioapp.R
import kotlinx.android.synthetic.main.item_servico.view.*

class ServicoAdapter(private var servicos: List<Servico>, var activity: Activity) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = View.inflate(activity, R.layout.item_servico,null)

        view.textNome.text = servicos[p0].nome
        view.textDescricao.text = servicos[p0].descricao

        return view
    }

    override fun getItem(p0: Int): Any {
        return servicos[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return servicos.size
    }

}