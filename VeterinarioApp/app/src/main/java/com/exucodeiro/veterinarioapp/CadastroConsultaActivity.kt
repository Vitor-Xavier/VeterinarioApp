package com.exucodeiro.veterinarioapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CadastroConsultaActivity : AppCompatActivity(), View.OnFocusChangeListener {

    override fun onFocusChange(p0: View?, p1: Boolean) {
        p0 as EditText
        if (p0.text.toString().trim() == "" && !p1)
            p0.error = "Inválido"
    }

    private var profissional: Profissional? = null
    private val animais = ArrayList<Animal>()
    private lateinit var adapter: AnimalSpinnerAdapter
    private var settings: LoginSettings? = null
    private var consulta: Consulta? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_consulta)
        title = getString(R.string.consulta)

        adapter = AnimalSpinnerAdapter(animais, this)
        loadData()
        spinnerAnimal.adapter = adapter

        settings = LoginSettings(this)

        editDate.setOnClickListener {
            val c = Calendar.getInstance()
            val ano = c.get(Calendar.YEAR)
            val mes = c.get(Calendar.MONTH)
            val dia = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                editDate.setText("$dayOfMonth/$monthOfYear/$year")
            }, ano, mes, dia)
            dpd.datePicker.minDate = c.timeInMillis
            dpd.show()
        }

        editHora.setOnClickListener {
            val c = Calendar.getInstance()
            val hora = c.get(Calendar.HOUR_OF_DAY)
            val min = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                editHora.setText("$hour:$minute")
            }, hora, min, true)
            tpd.show()
        }

        buttonConcluir.setOnClickListener {
            if (!validate())
                return@setOnClickListener

            salvarConsulta()
        }
    }

    private fun validate() : Boolean {
        if (editDate.text.toString() == "") {
            editDate.requestFocus()
            editDate.error = "Selecione uma data"
            return false
        }
        if (editDescricao.text.toString() == "") {
            editDescricao.requestFocus()
            editDescricao.error = "Informe o motivo"
            return false
        }
        if (editHora.text.toString() == "") {
            editHora.requestFocus()
            editHora.error = "Selecione o horário"
            return false
        }
        return true
    }

    private fun loadData() {
        async {
            val animalService = AnimalService()
            val loginSettings = LoginSettings(this@CadastroConsultaActivity)
            animais.addAll(animalService.getAnimais(loginSettings.login.id))

            uiThread {
                adapter.notifyDataSetChanged()
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

            val animal = adapter.getById(consulta?.animal?.animalId ?: 0)
            spinnerAnimal.setSelection(animal)
        }

        profissional = intent.getSerializableExtra("profissional") as Profissional

        textProfissionalDescricao.text = profissional?.crv
        textProfissionalNome.text = "${profissional?.nome} ${profissional?.sobrenome}"

        imageProfissional.loadUrl(profissional?.imagem ?: "")
    }

    private fun salvarConsulta() {
        val consultaService = ConsultaService()

        async {
            if (consulta != null && consulta?.consultaId != 0) {
                consultaService.alteraConsulta(consulta)
            } else {
                val local = Locale("pt", "BR")
                val df = SimpleDateFormat("dd/MM/yyyy HH:mm", local)
                val date = df.parse("${editDate.text.toString()} ${editHora.text.toString()}")

                val animal = adapter.getItem(spinnerAnimal.selectedItemPosition) as Animal

                consulta = Consulta(0, date, editDescricao.text.toString(), animal.animalId, animal, Consulta.AGUARDANDO, profissional?.profissionalId ?: 0, profissional as Profissional)

                consultaService.adicionaConsulta(consulta as Consulta)

                uiThread {
                    finish()
                }
            }
        }
    }

    fun ImageView.loadUrl(url: String?) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }
}
