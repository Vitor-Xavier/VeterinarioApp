package com.exucodeiro.veterinarioapp

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Animal
import com.exucodeiro.veterinarioapp.Models.Contato
import com.exucodeiro.veterinarioapp.Models.TipoAnimal
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.Services.AnimalService
import com.exucodeiro.veterinarioapp.Services.LoginSettings
import com.exucodeiro.veterinarioapp.Services.TipoAnimalService
import com.exucodeiro.veterinarioapp.Util.TipoAnimalAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_animal.*
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList

class CadastroAnimalActivity : AppCompatActivity() {
    var adapter: TipoAnimalAdapter? = null
    private val tipos = ArrayList<TipoAnimal>()
    var animal: Animal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_animal)

        adapter = TipoAnimalAdapter(tipos, this)
        loadData()
        spinnerTipoAnimal.adapter = adapter

        animal = intent.getSerializableExtra("animal") as Animal
        if (animal?.animalId != 0) {
            inputNome.setText(animal?.nome)
            val df = SimpleDateFormat("dd/MM/yyyy")
            inputDataNasc.setText(df.format(animal?.dataNascimento))
            imageIcone.loadUrl(animal?.imagem ?: "https://i.imgur.com/ckJhIUz.png")
            spinnerTipoAnimal.setSelection((adapter as TipoAnimalAdapter).getById(animal?.tipoAnimalId ?: 0))
        } else
            imageIcone.loadUrl("https://i.imgur.com/ckJhIUz.png")

        inputDataNasc.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                inputDataNasc.setText("$dayOfMonth/$monthOfYear/$year")
            }, year, month, day)
            dpd.show()
        }

        buttonConcluir.setOnClickListener {
            val df = SimpleDateFormat("dd/MM/yyyy")

            val settings = LoginSettings(this)

            val tipoAnimal = (adapter as TipoAnimalAdapter).getItem(spinnerTipoAnimal.selectedItemPosition) as TipoAnimal

            val animalIn = Animal(animal?.animalId ?: 0,
                    inputNome.text.toString(),
                    df.parse(inputDataNasc.text.toString()),
                    "https://i.imgur.com/ckJhIUz.png",
                    tipoAnimal.tipoAnimalId,
                    tipoAnimal,
                    settings.login.id,
                    Usuario(settings.login.id, "", "", "", null, ArrayList<Contato>())
            )

            val animalService = AnimalService()
            if (animal?.animalId == 0)
                toast(if (!animalService.adicionaAnimal(animalIn)) "Salvo" else "Não foi possível salvar")
            else
                toast(if (!animalService.atualizaAnimal(animalIn)) "Atualizado" else "Não foi possível atualizar")

            val handler = Handler()
            handler.postDelayed({ finish() }, 1500)
        }
    }

    fun salvaAnimal(animal: Animal) {
        async {
            val animalService = AnimalService()
            if (animal?.animalId == 0)
                animalService.adicionaAnimal(animal)
            else
                animalService.atualizaAnimal(animal)
        }
    }

    fun loadData() {
        val tipoAnimalService = TipoAnimalService()
        async {
            tipos.addAll(tipoAnimalService.getTipoAnimais())

            uiThread {
                adapter?.notifyDataSetChanged()
            }
        }
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null)
            Picasso.with(context).load(url).into(this)
    }
}
