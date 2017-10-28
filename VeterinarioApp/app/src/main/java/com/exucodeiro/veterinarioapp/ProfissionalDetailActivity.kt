package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Util.ProfissionalPageAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profissional_detail.*

class ProfissionalDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profissional_detail)

        val pageAdapter = ProfissionalPageAdapter(supportFragmentManager, this)
        viewPager.adapter = pageAdapter
        tabsProfissional.setupWithViewPager(viewPager)

        imageBack.loadUrl("http://decoclinic.com/wp-content/uploads/2016/11/camara2-Tartessos.jpg")

        imageIcon.loadUrl("https://cdn2.iconfinder.com/data/icons/medicine-4-1/512/vet-512.png")
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }
}
