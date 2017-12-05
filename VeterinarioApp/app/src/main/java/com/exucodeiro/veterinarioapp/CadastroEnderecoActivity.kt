package com.exucodeiro.veterinarioapp

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Endereco
import com.exucodeiro.veterinarioapp.Models.Login
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.Services.EnderecoService
import com.exucodeiro.veterinarioapp.Services.LoginSettings
import com.exucodeiro.veterinarioapp.Services.ProfissionalService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_endereco.*
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class CadastroEnderecoActivity : AppCompatActivity(), View.OnFocusChangeListener {
    var profissional: Profissional? = null
    var usuario: Usuario? = null
    var endereco: Endereco? = null
    var latitude: Double? = null
    var longitude: Double? = null
    private var locationManager : LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_endereco)
        title = getString(R.string.endereco)

        val prof = intent.getSerializableExtra("profissional") as Profissional?
        if (prof != null)
            loadProfissional()
        else
            loadUsuario()

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        try {
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 300L, 1000f, locationListener)
        } catch(ex: SecurityException) { }

        buttonLatLng.setOnClickListener {
            getEndereco()
        }

        buttonProximo.setOnClickListener {
            if (!validate())
                return@setOnClickListener

            salvarEndereco(false)
        }

        buttonConcluir.setOnClickListener {
            if (!validate())
                return@setOnClickListener

            salvarEndereco(true)
        }
    }

    private fun salvarEndereco(finalizar: Boolean) {
        async {
            if (endereco == null)
                loadEndereco()

            if (endereco?.latitude == 0.0 || endereco?.longitude == 0.0) {
                val enderecoService = EnderecoService()
                endereco = enderecoService.getLatLng(endereco as Endereco)
            }

            if (profissional != null) {
                if (profissional?.profissionalId == 0) {
                    val profissionalService = ProfissionalService()
                    profissional?.endereco = endereco
                    profissional = profissionalService.postProfissional(profissional as Profissional)
                    if (profissional != null) {
                        val loginSettings = LoginSettings(baseContext)
                        loginSettings.login = Login(profissional?.profissionalId ?: 0, profissional?.nomeUsuario ?: "", profissional?.senha ?: "", "Profissional")
                    }
                } else {
                    loadEndereco()
                    val enderecoService = EnderecoService()
                    enderecoService.atualizaEnderecoProfissional(profissional?.profissionalId ?: 0, endereco as Endereco)
                }

            } else {
                val enderecoService = EnderecoService()
                loadEndereco()
                usuario?.endereco = endereco
                enderecoService.atualizaEnderecoUsuario(usuario?.usuarioId ?: 0, endereco as Endereco)
            }

            uiThread {
                if (finalizar) {
                    val intentMain = Intent(this@CadastroEnderecoActivity, MainActivity::class.java)
                    intentMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intentMain)
                } else {
                    val intentContato = Intent(this@CadastroEnderecoActivity, ContatoActivity::class.java)
                    intentContato.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    if (usuario != null)
                        intentContato.putExtra("usuario", usuario)
                    if (profissional != null)
                        intentContato.putExtra("profissional", profissional as Profissional)
                    startActivity(intentContato)
                }
            }
        }
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        (p0 as EditText)
        if (p0.text.toString().trim() == "" && !p1)
            p0.error = "Conteudo inválido"
    }

    private fun validate() : Boolean {
        if (inputLogradouro.text.toString().trim() == "") {
            inputLogradouro.requestFocus()
            inputLogradouro.error = "Ex.: Avenida Pio XII"
            return false
        }
        if (inputNumero.text.toString().trim() == "") {
            inputNumero.requestFocus()
            inputNumero.error = "Ex.: 1255"
            return false
        }
        if (inputBairro.text.toString().trim() == "") {
            inputBairro.requestFocus()
            inputBairro.error = "Ex.: Vila Virgínia"
            return false
        }
        if (inputCEP.text.toString().trim() == "") {
            inputCEP.requestFocus()
            inputCEP.error = "Ex.: 14030-250"
            return false
        }
        if (inputCidade.text.toString().trim() == "") {
            inputCidade.requestFocus()
            inputCidade.error = "Ex.: Ribeirão Preto"
            return false
        }
        if (inputEstado.text.toString().trim() == "") {
            inputEstado.requestFocus()
            inputEstado.error = "Ex.: São Paulo"
            return false
        }
        return true
    }

    private fun getLatLng() {
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
            } else
                uiThread {
                    toast("Serviço de localização indisponível")
                }
        }
    }

    private fun loadProfissional() {
        profissional = intent.getSerializableExtra("profissional") as Profissional?

        textNome.text = profissional?.nome
        imageIcone.loadUrl(profissional?.icone)

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

    private fun loadUsuario() {
        usuario = intent.getSerializableExtra("usuario") as Usuario?

        textNome.text = usuario?.nome
        imageIcone.loadUrl(usuario?.imagem)

        inputLogradouro.setText(usuario?.endereco?.logradouro)
        inputNumero.setText(usuario?.endereco?.numero.toString())
        inputComplemento.setText(usuario?.endereco?.complemento)
        inputBairro.setText(usuario?.endereco?.bairro)
        inputCEP.setText(usuario?.endereco?.cep)
        inputCidade.setText(usuario?.endereco?.cidade)
        inputEstado.setText(usuario?.endereco?.estado)

        if (usuario?.endereco != null)
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
            latitude = location.latitude
            longitude = location.longitude
            locationManager?.removeUpdates(this)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

}
