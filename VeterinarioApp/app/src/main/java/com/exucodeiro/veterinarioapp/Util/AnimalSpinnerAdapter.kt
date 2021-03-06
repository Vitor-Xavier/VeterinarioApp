package com.exucodeiro.veterinarioapp.Util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Animal
import com.exucodeiro.veterinarioapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_animal_spinner.view.*

class AnimalSpinnerAdapter(private var animais:List<Animal>, var activity: Activity) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = View.inflate(activity, R.layout.item_animal_spinner,null)

        view.textNome.text = animais[p0].nome
        view.imageAnimal.loadUrl(animais[p0].imagem)

        return view
    }

    fun ImageView.loadUrl(url: String?) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }

    override fun getItem(p0: Int): Any {
        return animais[p0]
    }

    fun getById(id: Int) : Int {
        val tipo = animais.find { it.animalId == id }
        return animais.indexOf(tipo)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return animais.size
    }

}