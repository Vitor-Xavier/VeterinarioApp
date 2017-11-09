package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Services.ProfissionalService

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.async

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    val list = ArrayList<Profissional>()

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val rib = LatLng(-21.1767, -47.8208)
        for (it in list) {
            val rib = LatLng(it.endereco?.latitude ?: -21.1767, it.endereco?.longitude ?: -47.8208)
            mMap.addMarker(MarkerOptions().position(rib).title("${it.nome} ${it.sobrenome}"))
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rib))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0F))

        loadData()
    }

    fun loadData() {
        async {
            val profissionalService = ProfissionalService()
            list.addAll(profissionalService.getProfissionais(-21.1767, -47.8208))
            val rib = LatLng(-0.1767, -0.8208)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(rib))
        }
    }
}
