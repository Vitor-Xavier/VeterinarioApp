package com.exucodeiro.veterinarioapp

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Animal
import com.exucodeiro.veterinarioapp.Models.Contato
import com.exucodeiro.veterinarioapp.Models.TipoAnimal
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.Services.AnimalService
import com.exucodeiro.veterinarioapp.Services.LoginSettings
import com.exucodeiro.veterinarioapp.Services.TipoAnimalService
import com.exucodeiro.veterinarioapp.Services.UploadService
import com.exucodeiro.veterinarioapp.Util.TipoAnimalAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_animal.*
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.widget.Toast
import android.content.ActivityNotFoundException
import android.net.Uri
import android.graphics.Bitmap
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import com.exucodeiro.veterinarioapp.Util.ImageUtils

class CadastroAnimalActivity : AppCompatActivity() {
    private lateinit var adapter: TipoAnimalAdapter
    private val tipos = ArrayList<TipoAnimal>()
    private var animal: Animal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_animal)
        title = getString(R.string.animal)

        adapter = TipoAnimalAdapter(tipos, this)
        loadData()
        spinnerTipoAnimal.adapter = adapter

        animal = intent.getSerializableExtra("animal") as Animal
        if (animal?.animalId != 0) {
            inputNome.setText(animal?.nome)
            val df = SimpleDateFormat("dd/MM/yyyy")
            inputDataNasc.setText(df.format(animal?.dataNascimento))
            imageIcone.loadUrl(animal?.imagem ?: "https://i.imgur.com/ckJhIUz.png")
            spinnerTipoAnimal.setSelection(adapter.getById(animal?.tipoAnimalId ?: 0))
        } else
            imageIcone.loadUrl("https://i.imgur.com/ckJhIUz.png")

        inputDataNasc.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, _, monthOfYear, dayOfMonth ->
                inputDataNasc.setText("$dayOfMonth/$monthOfYear/$year")
            }, year, month, day)
            dpd.show()
        }

        buttonConcluir.setOnClickListener {
            val df = SimpleDateFormat("dd/MM/yyyy")

            val settings = LoginSettings(this)

            val tipoAnimal = adapter.getItem(spinnerTipoAnimal.selectedItemPosition) as TipoAnimal

            val animalIn = Animal(animal?.animalId ?: 0,
                    inputNome.text.toString(),
                    df.parse(inputDataNasc.text.toString()),
                    "https://i.imgur.com/ckJhIUz.png",
                    tipoAnimal.tipoAnimalId,
                    tipoAnimal,
                    settings.login.id,
                    Usuario(settings.login.id, "", "", "", null, ArrayList())
            )
            salvaAnimal(animalIn)

        }

        imageIcone.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 23)
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 14)
                else if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 15)
                else
                    selectImageInAlbum()
        }
    }

    private fun salvaAnimal(animal: Animal) {
        async {
            val animalService = AnimalService()
            if (animal.animalId == 0) {
                if (!animalService.adicionaAnimal(animal))
                    toast("Não foi possível adicionar o animal")
            } else {
                if (!animalService.atualizaAnimal(animal))
                    toast("Não foi possível alterar os dados do animal")
            }

            uiThread {
                finish()
            }
        }
    }

    private fun loadData() {
        val tipoAnimalService = TipoAnimalService()
        async {
            tipos.addAll(tipoAnimalService.getTipoAnimais())

            uiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun ImageView.loadUrl(url: String?) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }

    fun ImageView.loadUrl(url: Int) {
        Picasso.with(context).load(url).into(this)
    }

    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_SELECT_IMAGE_IN_ALBUM -> {
                    performCrop(Uri.parse(data?.data?.toString()))
                }
                PIC_CROP -> {
                    val extras = data?.extras
                    val selectedBitmap = extras?.getParcelable<Bitmap>("data") as Bitmap
                    val uri = ImageUtils.getImageUri(this, selectedBitmap)
                    imageIcone.setImageBitmap(selectedBitmap)

                    val uploadService = UploadService()
                    async {
                        uploadService.enviarImagem(baseContext, uri.toString()) //data?.data?.toString() ?: ""
                    }
                }
            }
        }
    }

    private fun takePhoto() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
        }
    }

    private fun performCrop(picUri: Uri) {
        try {
            val cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(picUri, "image/*")
            cropIntent.putExtra("crop", true)
            cropIntent.putExtra("aspectX", 1)
            cropIntent.putExtra("aspectY", 1)
            cropIntent.putExtra("outputX", 128)
            cropIntent.putExtra("outputY", 128)
            cropIntent.putExtra("return-data", true)
            startActivityForResult(cropIntent, PIC_CROP)
        } catch (anfe: ActivityNotFoundException) {
            val errorMessage = "Sem suporte a ação de recorte"
            val toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            toast("Permission: "+permissions[0]+ "was "+grantResults[0])
            selectImageInAlbum()
        }
    }

    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        private val PIC_CROP = 2
    }

}
