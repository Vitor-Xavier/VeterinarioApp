package com.exucodeiro.veterinarioapp

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.exucodeiro.veterinarioapp.Util.MainPageAdapter
import kotlinx.android.synthetic.main.fragment_profissional.*
import android.view.MenuInflater
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class ProfissionalFragment : Fragment() {
    private var locationManager : LocationManager? = null
    private var profissionais = ArrayList<Profissional>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        try {
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 200L, 1000f, locationListener)
        } catch(ex: SecurityException) {
            loadData(-21.0, -47.0)
        } catch(ex: Exception) {
            loadData(-21.0, -47.0)
        }

        return inflater!!.inflate(R.layout.fragment_profissional, container, false)
    }

    private fun loadData(lat: Double, lng: Double) {
        async {
            val profissionalService = ProfissionalService()
            profissionais.addAll(profissionalService.getProfissionais(lat, lng))

            uiThread {
                if(!isAdded) return@uiThread
                val pageAdapter = MainPageAdapter(childFragmentManager, profissionais, lat, lng)
                viewPager.adapter = pageAdapter
                tabsProfissional.setupWithViewPager(viewPager)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.title = getString(R.string.profissionais)
    }

    private val locationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            loadData(location.latitude, location.longitude)
            locationManager?.removeUpdates(this)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

}
