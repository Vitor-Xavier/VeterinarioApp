package com.exucodeiro.veterinarioapp

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Endereco
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Services.EnderecoService
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_endereco.*
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class CadastroEnderecoActivity : AppCompatActivity() {
    var profissional: Profissional? = null
    val profissionalService = ProfissionalService()
    var endereco: Endereco? = null
    var latitude: Double? = null
    var longitude: Double? = null
    private var locationManager : LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_endereco)
        title = getString(R.string.endereco)

        loadData()

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        try {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
        } catch(ex: SecurityException) { }

        buttonProximo.setOnClickListener {
//            val endereco = Endereco(0,
//                    inputLogradouro.text.toString(),
//                    Integer.parseInt(inputNumero.text.toString()),
//                    inputComplemento.text.toString(),
//                    inputBairro.text.toString(),
//                    inputCEP.text.toString(),
//                    inputCidade.text.toString(),
//                    inputEstado.text.toString(),
//                    0.0,
//                    0.0)
//            profissional?.endereco = endereco
//
//            if(!profissionalService.postProfissional(profissional as Profissional))
//                toast("Erro")
            getEndereco()
        }
    }

    fun getLatLng() {
        val enderecoService = EnderecoService()
        async {
            if (endereco == null)
                loadEndereco()

            endereco = enderecoService.getLatLng(endereco as Endereco)

            uiThread {
                toast("${endereco?.latitude}:${endereco?.longitude}")
            }

        }
    }

    private fun getEndereco() {
        val enderecoService = EnderecoService()
        async {
            if (latitude != null && longitude != null) {
                endereco = enderecoService.getEnderecoCompleto(latitude as Double, longitude as Double)

                if (endereco != null) {
                    uiThread {
                        inputLogradouro.setText(endereco?.logradouro)
                        inputNumero.setText("${endereco?.numero}")
                        inputComplemento.setText(endereco?.complemento)
                        inputBairro.setText(endereco?.bairro)
                        inputCEP.setText(endereco?.cep)
                        inputCidade.setText(endereco?.cidade)
                        inputEstado.setText(endereco?.estado)
                    }
                }
            } else {
                SHOW_LOCATION = 1
                toast("Serviço de localização indisponível")
            }
        }
    }

    private fun loadData() {
        profissional = intent.getSerializableExtra("profissional") as Profissional

        textNome.text = profissional?.nome

        inputLogradouro.setText(profissional?.endereco?.logradouro)
        inputNumero.setText("${profissional?.endereco?.numero}")
        inputComplemento.setText(profissional?.endereco?.complemento)
        inputBairro.setText(profissional?.endereco?.bairro)
        inputCEP.setText(profissional?.endereco?.cep)
        inputCidade.setText(profissional?.endereco?.cidade)
        inputEstado.setText(profissional?.endereco?.estado)

        if (profissional?.endereco != null)
            loadEndereco()
    }

    private fun loadEndereco() {
        endereco = Endereco(0,
                inputLogradouro.text.toString(),
                Integer.parseInt(inputNumero.text.toString()),
                inputComplemento.text.toString(),
                inputBairro.text.toString(),
                inputCEP.text.toString(),
                inputCidade.text.toString(),
                inputEstado.text.toString(),
                endereco?.latitude ?: 0.0,
                endereco?.longitude ?: 0.0)
    }

    fun ImageView.loadUrl(url: String?) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }

    private val locationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            if (SHOW_LOCATION == 1) {
                latitude = location.latitude
                longitude = location.longitude
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
