package com.exucodeiro.veterinarioapp

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.exucodeiro.veterinarioapp.Util.MainPageAdapter
import kotlinx.android.synthetic.main.fragment_profissional.*
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class ProfissionalFragment : Fragment() {
    private var locationManager: LocationManager? = null
    private var profissionais = ArrayList<Profissional>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        try {
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            var location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            loadData(location?.latitude ?: -21.0, location?.longitude ?: -47.0)
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

}
