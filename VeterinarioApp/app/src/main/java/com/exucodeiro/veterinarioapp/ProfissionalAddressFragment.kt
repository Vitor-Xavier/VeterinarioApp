package com.exucodeiro.veterinarioapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profissional_address.*

class ProfissionalAddressFragment : Fragment(), OnMapReadyCallback {
    var profissional: Profissional? = null
    private lateinit var mMap: GoogleMap

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val proMarker = LatLng(profissional?.endereco?.latitude ?: -21.1767, profissional?.endereco?.longitude ?: -47.8208)

        mMap.addMarker(MarkerOptions().position(proMarker).title("${profissional?.nome} ${profissional?.sobrenome}"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(proMarker))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0F))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_profissional_address, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        profissional = arguments.getSerializable("profissional") as Profissional

        textEndereco.text = profissional?.endereco.toString()

        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map_pro) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

}
