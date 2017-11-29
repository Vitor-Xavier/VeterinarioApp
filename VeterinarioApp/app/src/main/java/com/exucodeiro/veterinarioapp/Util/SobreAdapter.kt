package com.exucodeiro.veterinarioapp.Util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Sobre
import com.exucodeiro.veterinarioapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_sobre.view.*

class SobreAdapter(private var sobreList:List<Sobre>, var activity: Activity) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = View.inflate(activity, R.layout.item_sobre,null)

        view.textDetail.text = sobreList[p0].texto
        view.imageIcone.loadUrl(sobreList[p0].icone)

        return view
    }

    fun ImageView.loadUrl(drawable: Int) {
        Picasso.with(context).load(drawable).into(this)
    }

    override fun getItem(p0: Int): Any {
        return sobreList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return sobreList.size
    }

}