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
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.Services.LoginService
import com.exucodeiro.veterinarioapp.Services.UploadService
import com.exucodeiro.veterinarioapp.Services.UsuarioService
import com.exucodeiro.veterinarioapp.Util.ImageUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_usuario.*
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class CadastroUsuarioActivity : AppCompatActivity(), View.OnFocusChangeListener {
    private var usuario: Usuario? = null
    private var imageUrl: String = "https://veterinarioblob.blob.core.windows.net/images/userDefault.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        title = getString(R.string.usuario)

        buttonProximo.setOnClickListener {
            async {
                if (usuario?.usuarioId == 0) {
                    val loginService = LoginService()
                    if (!loginService.verificaUsuario(inputUsername.text.toString())) {
                        uiThread {
                            inputUsername.requestFocus()
                            inputUsername.error = "Nome de usuário indisponível"
                        }
                        return@async
                    }
                }
                var valid = false
                uiThread {
                    valid = !validate()
                }
                if (!valid)
                    return@async

                val usuarioService = UsuarioService()
                if (usuario == null || usuario?.usuarioId == 0) {
                    loadUsuario()
                    usuarioService.adicionaUsuario(usuario as Usuario)
                } else
                    usuarioService.atualizaUsuario(usuario as Usuario)

                val intentEndereco = Intent(this@CadastroUsuarioActivity, CadastroEnderecoActivity::class.java)
                intentEndereco.putExtra("usuario", usuario)
                startActivity(intentEndereco)
            }
        }

        buttonConcluir.setOnClickListener {
            async {
                if (usuario?.usuarioId == 0) {
                    val loginService = LoginService()
                    if (!loginService.verificaUsuario(inputUsername.text.toString())) {
                        uiThread {
                            inputUsername.requestFocus()
                            inputUsername.error = "Nome de usuário indisponível"
                        }
                        return@async
                    }
                }
                var valid = false
                uiThread {
                    valid = validate()
                    toast(if (valid) "true" else "false")
                }
                if (!valid)
                    return@async


                val usuarioService = UsuarioService()
                if(usuario == null || usuario?.usuarioId == 0) {
                    loadUsuario()
                    usuario = usuarioService.adicionaUsuario(usuario as Usuario)
                } else {
                    loadUsuario()
                    usuarioService.atualizaUsuario(usuario as Usuario)
                }

                val intCadUsr = Intent(this@CadastroUsuarioActivity, MainActivity::class.java)
                intCadUsr.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intCadUsr)
            }
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

    override fun onFocusChange(p0: View?, p1: Boolean) {
        (p0 as EditText)
        if (p0.text.toString().trim() == "" && !p1)
            p0.error = "${p0.hint} não pode estar vazio"
    }

    private fun validate() : Boolean {
        if (inputNome.text.toString().trim() == "") {
            inputNome.requestFocus()
            inputNome.error = "Informe seu nome"
            return false
        }
        if (inputSobrenome.text.toString().trim() == "") {
            inputSobrenome.requestFocus()
            inputSobrenome.error = "Informe seu sobrenome"
            return false
        }
        if (inputUsername.text.toString().trim() == "") {
            inputUsername.requestFocus()
            inputUsername.error = "Nome de usuário inválido"
            return false
        }
        if (inputPass.text.toString().trim() == "") {
            inputPass.requestFocus()
            inputPass.error = "Informe uma senha"
            return false
        }
        return true
    }

    private fun loadData() {
        usuario = intent.getSerializableExtra("usuario") as Usuario?

        if (usuario != null) {
            inputNome.setText(usuario?.nome)
            inputSobrenome.setText(usuario?.sobrenome)
            if (usuario?.usuarioId != 0)
                inputUsername.isEnabled = false
            inputUsername.setText(usuario?.nomeUsuario)
            inputPass.setText(usuario?.senha)

            imageIcone.loadUrl(usuario?.imagem ?: "")
        } else
            loadUsuario()
    }

    private fun loadUsuario() {
        usuario = Usuario(usuario?.usuarioId ?: 0,
                inputNome.text.toString(),
                inputSobrenome.text.toString(),
                inputUsername.text.toString(),
                inputPass.text.toString(),
                imageUrl,
                null,
                ArrayList())
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
                    performCrop(Uri.parse(data?.data?.toString()))
                }
                PIC_CROP -> {
                    val extras = data?.extras
                    val selectedBitmap = extras?.getParcelable<Bitmap>("data") as Bitmap
                    val uri = ImageUtils.getImageUri(this, selectedBitmap)
                    imageIcone.setImageBitmap(selectedBitmap)

                    async {
                        val uploadService = UploadService()

                        imageUrl = uploadService.enviarImagem(baseContext, uri.toString(), "imageUsuario") ?: imageUrl
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