package com.exucodeiro.veterinarioapp

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Services.UploadService
import com.exucodeiro.veterinarioapp.Util.ImageUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_profissional.*
import org.jetbrains.anko.async
import org.jetbrains.anko.toast

class CadastroProfissionalActivity : AppCompatActivity(), View.OnFocusChangeListener {
    private var imageUrl = "http://decoclinic.com/wp-content/uploads/2016/11/camara2-Tartessos.jpg"
    private var iconeUrl = "http://www.kibbypark.com/wp-content/uploads/2015/08/wellness-icon.png"

    override fun onFocusChange(p0: View?, p1: Boolean) {
        (p0 as EditText)
        if (p0.text.toString().trim() == "" && !p1) {
            p0.error = "Texto inválido"
        }
    }

    private var profissional: Profissional? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_profissional)
        title = getString(R.string.profissional)

        inputNome.onFocusChangeListener = this
        inputSobrenome.onFocusChangeListener = this
        inputUsername.onFocusChangeListener = this
        inputPass.onFocusChangeListener = this
        inputDescricao.onFocusChangeListener = this

        buttonProximo.setOnClickListener {
            if (!validate())
                return@setOnClickListener

            val intCadEnd = Intent(this, CadastroEnderecoActivity::class.java)
            val profissional = Profissional(0,
                    inputNome.text.toString(),
                    inputSobrenome.text.toString(),
                    inputDescricao.text.toString(),
                    imageUrl,
                    iconeUrl,
                    inputCRV.text.toString(),
                    0,
                    null,
                    ArrayList(),
                    ArrayList(),
                    false)
            intCadEnd.putExtra("profissional", profissional)
            startActivity(intCadEnd)
        }

        imageBackground.setOnClickListener {
            IMAGE_BACKGROUND = 1
            if (Build.VERSION.SDK_INT >= 23)
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 12)
                else
                    selectImageInAlbum()
        }

        imageIcone.setOnClickListener {
            IMAGE_BACKGROUND = 0
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

        loadData()
    }

    private fun validate() : Boolean {
        if (inputUsername.text.toString().trim() == "") {
            inputUsername.requestFocus()
            return false
        }
        if (inputPass.text.toString().trim() == "") {
            inputPass.requestFocus()
            return false
        }
        if (inputNome.text.toString().trim() == "") {
            inputNome.requestFocus()
            return false
        }
        if (inputSobrenome.text.toString().trim() == "") {
            inputSobrenome.requestFocus()
            return false
        }
        if (inputDescricao.text.toString().trim() == "") {
            inputDescricao.requestFocus()
            return false
        }
        return true
    }

    private fun loadData() {
        profissional = intent.getSerializableExtra("profissional") as Profissional?

        if(profissional != null) {
            inputNome.setText(profissional?.nome)
            inputSobrenome.setText(profissional?.sobrenome)
            inputCRV.setText(profissional?.crv)
            inputDescricao.setText(profissional?.descricao)

            imageIcone.loadUrl(profissional?.icone)
            imageBackground.loadUrl(profissional?.imagem)
        }
    }

    fun ImageView.loadUrl(url: String?) {
        if (url != null && url != "")
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
                    if (IMAGE_BACKGROUND == 0)
                        performCrop(Uri.parse(data?.data?.toString()), 128, 128, 1, 1)
                    else
                        performCrop(Uri.parse(data?.data?.toString()), 270, 480, 16, 9)
                }
                PIC_CROP -> {
                    val extras = data?.extras
                    val selectedBitmap = extras?.getParcelable<Bitmap>("data") as Bitmap
                    val uri = ImageUtils.getImageUri(this, selectedBitmap)

                    async {
                        val uploadService = UploadService()

                        if (IMAGE_BACKGROUND == 0) {
                            imageIcone.setImageBitmap(selectedBitmap)
                            imageUrl = uploadService.enviarImagem(baseContext, uri.toString(), "imageBackProf") ?: imageUrl
                        } else {
                            imageBackground.setImageBitmap(selectedBitmap)
                            iconeUrl = uploadService.enviarImagem(baseContext, uri.toString(), "imageIconeProf") ?: iconeUrl
                        }

                    }
                }
            }
        } else {
            toast("Não foi possível identificar a imagem selecionada.")
        }
    }

    private fun performCrop(picUri: Uri, height: Int, width: Int, aspectX: Int, aspectY: Int) {
        try {
            val cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(picUri, "image/*")
            cropIntent.putExtra("crop", true)
            cropIntent.putExtra("aspectX", aspectX)
            cropIntent.putExtra("aspectY", aspectY)
            cropIntent.putExtra("outputX", width)
            cropIntent.putExtra("outputY", height)
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

    private fun takePhoto() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
        }
    }
    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        private var IMAGE_BACKGROUND = 0
        private val PIC_CROP = 2
    }

}
