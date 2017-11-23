package com.exucodeiro.veterinarioapp

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profissional_map.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class ProfissionalMapFragment : Fragment(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var locationManager : LocationManager? = null

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        try {
            mMap?.isMyLocationEnabled = true
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100L, 1000f, locationListener)
        } catch(ex: SecurityException) {
            toast("Serviço de localização indiponível")
        }

        val rib = LatLng(-21.1767, -47.8208)
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(rib,14.5F))

        loadData()
    }

    fun loadData() {
        val profissionalService = ProfissionalService()
        async {
            val profissionais = profissionalService.getProfissionais(-21.1767, -47.8208)
            uiThread {
                for (profissional in profissionais) {
                    val pro = LatLng(profissional.endereco?.latitude ?: -21.1767, profissional.endereco?.longitude ?: -47.8208)
                    mMap?.addMarker(MarkerOptions().position(pro).title("${profissional.nome} ${profissional.sobrenome}"))
                }
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager?

        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (Build.VERSION.SDK_INT >= 23)
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 12)
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 13)
        return inflater!!.inflate(R.layout.fragment_profissional_map, container, false)
    }

    fun ImageView.loadUrl(url: String?) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }
    }

    private val locationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            val latLng = LatLng(location.latitude, location.longitude)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14.5F)
            mMap?.animateCamera(cameraUpdate)
            locationManager?.removeUpdates(this)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

}
