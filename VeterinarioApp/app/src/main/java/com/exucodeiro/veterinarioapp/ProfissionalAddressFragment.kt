package com.exucodeiro.veterinarioapp


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profissional_address.*

class ProfissionalAddressFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_profissional_address, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val profissional = arguments.getSerializable("profissional") as Profissional

        textEndereco.text = profissional.endereco.toString()
        imageMap.loadUrl("https://snazzy-maps-cdn.azureedge.net/assets/1243-xxxxxxxxxxx.png?v=20170626083204")

        super.onActivityCreated(savedInstanceState)
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

}
