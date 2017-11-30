package com.exucodeiro.veterinarioapp

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
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
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import java.util.*

class ProfissionalMapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private var mMap: GoogleMap? = null
    private lateinit var hashMap: HashMap<Marker, Profissional>

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.setOnMarkerClickListener(this)

        try {
            mMap?.isMyLocationEnabled = true
            //locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100L, 1000f, locationListener)
        } catch(ex: SecurityException) {
            toast("Serviço de localização indiponível")
        }

        val inicial = LatLng(arguments.getDouble(ARG_LATITUDE), arguments.getDouble(ARG_LONGITUDE))
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(inicial,14.5F))

        loadData()
    }

    fun loadData() {
        hashMap = HashMap()

        val profissionais = arguments.getSerializable(ARG_PROFISSIONAL) as ArrayList<Profissional>
        for (profissional in profissionais) {
            val pro = LatLng(profissional.endereco?.latitude ?: -21.1767, profissional.endereco?.longitude ?: -47.8208)
            val marker =  mMap?.addMarker(MarkerOptions().position(pro).title("${profissional.nome} ${profissional.sobrenome}"))
            hashMap.put(marker as Marker, profissional)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        val intentPro = Intent(context, ProfissionalDetailActivity::class.java)
        intentPro.putExtra("profissional", hashMap[p0])
        startActivity(intentPro)
        return true
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

    companion object {
        private val ARG_PROFISSIONAL = "profissionais"
        private val ARG_LATITUDE = "lat"
        private val ARG_LONGITUDE = "lng"

        fun newInstance(profissionais: ArrayList<Profissional>, lat: Double, lng: Double): ProfissionalMapFragment {
            val fragment = ProfissionalMapFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARG_PROFISSIONAL, profissionais)
            bundle.putDouble(ARG_LATITUDE, lat)
            bundle.putDouble(ARG_LONGITUDE, lng)
            fragment.arguments = bundle
            return fragment
        }
    }

}
