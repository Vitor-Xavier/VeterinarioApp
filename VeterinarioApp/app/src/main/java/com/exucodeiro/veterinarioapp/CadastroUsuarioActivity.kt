package com.exucodeiro.veterinarioapp

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.SyncStateContract
import android.support.v4.app.ActivityCompat
import android.widget.ImageView
import android.widget.Toast
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.Services.UploadService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_usuario.*
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import java.io.ByteArrayOutputStream

class CadastroUsuarioActivity : AppCompatActivity() {
    private var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        title = "Usuário"

        buttonProximo.setOnClickListener {
            val it = Intent(this, CadastroEnderecoActivity::class.java)
            startActivity(it)
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

        loadData()
    }

    fun loadData() {
        usuario = intent.getSerializableExtra("usuario") as Usuario?

        if (usuario != null) {
            inputNome.setText(usuario?.nome)
            inputSobrenome.setText(usuario?.sobrenome)
            imageIcone.loadUrl(usuario?.imagem ?: "")
        }
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }

    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_SELECT_IMAGE_IN_ALBUM -> {
                    performCrop(Uri.parse(data?.data?.toString()))
                }
                PIC_CROP -> {
                    val extras = data?.extras
                    val selectedBitmap = extras?.getParcelable<Bitmap>("data") as Bitmap
                    val uri = getImageUri(this, selectedBitmap)
                    imageIcone.setImageBitmap(selectedBitmap)

                    val uploadService = UploadService()
                    async {
                        uploadService.enviarImagem(baseContext, uri.toString()) //data?.data?.toString() ?: ""
                    }
                }
            }
        } else {
            toast("Não foi possível identificar a imagem selecionada.")
        }
    }

    fun takePhoto() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
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
            selectImageInAlbum()
        } else
            toast("Permissão não concedida")
    }

    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        private val PIC_CROP = 2
    }
}