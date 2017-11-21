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
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class ProfissionalMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var locationManager : LocationManager? = null

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val rib = LatLng(-21.1767, -47.8208)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rib))

        async {
            val profissionalService = ProfissionalService()
            val list = profissionalService.getProfissionais(-21.1767, -47.8208)
            uiThread {
                for (it in list) {
                    val pro = LatLng(it.endereco?.latitude ?: -21.1767, it.endereco?.longitude ?: -47.8208)
                    mMap.addMarker(MarkerOptions().position(pro).title("${it.nome} ${it.sobrenome}"))
                }
            }

        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(rib))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0F))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager?

        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)

        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available");
        }

        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }
    }

    private val locationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            if (SHOW_LOCATION == 1) {
                val lat_lng = LatLng(location.latitude, location.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(lat_lng))
                SHOW_LOCATION = 0
            }
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    companion object {
        private var SHOW_LOCATION = 1
    }

}
