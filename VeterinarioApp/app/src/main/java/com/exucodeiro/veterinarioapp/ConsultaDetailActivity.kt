package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Consulta
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_consulta_detail.*

class ConsultaDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var consulta: Consulta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_detail)

        loadData()

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map_consulta) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val proMarker = LatLng(consulta.profissional?.endereco?.latitude ?: -21.1767, consulta.profissional?.endereco?.longitude ?: -47.8208)

        mMap.addMarker(MarkerOptions().position(proMarker).title("${consulta.profissional?.nome} ${consulta.profissional?.sobrenome}"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(proMarker))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0F))
    }

    fun loadData() {
        consulta = intent.getSerializableExtra("consulta") as Consulta

        textAnimalNome.text = consulta.animal.nome
        textDono.text = "${consulta.animal.dono?.nome} ${consulta.animal.dono?.sobrenome}"
        imageAnimal.loadUrl(consulta.animal.imagem)
        imageTipoAnimal.loadUrl(consulta.animal.tipoAnimal.icone)

        textProfissionalNome.text = "${consulta.profissional.nome} ${consulta.profissional.sobrenome}"
        textProfissionalDescricao.text = consulta.profissional.crv
        imageProfissional.loadUrl(consulta.profissional.icone)

        textData.text = consulta.getDataFormatada()
        textDescricao.text = consulta.descricao
        textEndereco.text = consulta.profissional.endereco.toString()
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

}
