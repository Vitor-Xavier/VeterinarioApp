package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Animal
import com.exucodeiro.veterinarioapp.Models.Consulta
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Services.AnimalService
import com.exucodeiro.veterinarioapp.Services.ConsultaService
import com.exucodeiro.veterinarioapp.Services.LoginSettings
import com.exucodeiro.veterinarioapp.Util.AnimalSpinnerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_consulta.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*

class CadastroConsultaActivity : AppCompatActivity() {
    var profissional: Profissional? = null
    val animais = ArrayList<Animal>()
    var adapter: AnimalSpinnerAdapter? = null
    var settings: LoginSettings? = null
    var consulta: Consulta? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_consulta)
        title = "Consulta"

        adapter = AnimalSpinnerAdapter(animais, this)
        loadData()
        spinnerAnimal.adapter = adapter

        settings = LoginSettings(this)

        buttonConcluir.setOnClickListener {
            salvarConsulta()
        }
    }

    fun loadData() {
        async {
            val animalService = AnimalService()

            animais.addAll(animalService.getAnimal(settings?.login?.id ?: 1))

            uiThread {
                adapter?.notifyDataSetChanged()
            }
        }

        if(consulta != null && consulta?.consultaId != 0) {
            val df = SimpleDateFormat("dd/MM/yyyy")
            val hr = SimpleDateFormat("HH:mm:ss")

            textProfissionalNome.text = "${consulta?.profissional?.nome} ${consulta?.profissional?.sobrenome}"
            textProfissionalDescricao.text = consulta?.profissional?.crv

            imageProfissional.loadUrl(consulta?.profissional?.icone ?: "")

            editDescricao.setText(consulta?.descricao)
            editDate.setText(df.format(consulta?.data))
            editHora.setText(hr.format(consulta?.data))

            val animal = adapter?.getById(consulta?.animal?.animalId ?: 0)
            spinnerAnimal.setSelection(animal ?: 1)
        }

        profissional = intent.getSerializableExtra("profissional") as Profissional

        textProfissionalDescricao.text = profissional?.crv
        textProfissionalNome.text = "${profissional?.nome} ${profissional?.sobrenome}"

        imageProfissional.loadUrl(profissional?.imagem ?: "")
    }

    fun salvarConsulta() {
        val consultaService = ConsultaService()

        if (consulta != null && consulta?.consultaId != 0) {
            consultaService.alteraConsulta(consulta)
        } else {
            val local = Locale("pt", "BR")
            val df = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", local)
            val cal = Calendar.getInstance()
            val tst = "${editDate.text.toString()} ${editHora.text.toString()}"
            val date = df.parse("${editDate.text.toString()} ${editHora.text.toString()}")

            val animal = adapter?.getItem(spinnerAnimal.selectedItemPosition) as Animal

            consulta = Consulta(0, date, editDescricao.text.toString(), animal.animalId, animal, profissional?.profissionalId ?: 0, profissional as Profissional)

            consultaService.adicionaConsulta(consulta as Consulta)
        }
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }
}
