package com.exucodeiro.veterinarioapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Contato
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Models.Servico
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cadastro_profissional.*
import org.jetbrains.anko.toast

class CadastroProfissionalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_profissional)
        title = "Profissional"

        //imageBackground.loadUrl("http://decoclinic.com/wp-content/uploads/2016/11/camara2-Tartessos.jpg")

        //imageIcone.loadUrl("http://www.kibbypark.com/wp-content/uploads/2015/08/wellness-icon.png")

        buttonProximo.setOnClickListener {
            val it = Intent(this, CadastroEnderecoActivity::class.java)
            val profissional = Profissional(0,
                    inputNome.text.toString(),
                    inputSobrenome.text.toString(),
                    "http://decoclinic.com/wp-content/uploads/2016/11/camara2-Tartessos.jpg",
                    "http://www.kibbypark.com/wp-content/uploads/2015/08/wellness-icon.png",
                    inputCRV.text.toString(),
                    0,
                    null,
                    ArrayList<Contato>(),
                    ArrayList<Servico>(),
                    false)
            it.putExtra("profissional", profissional)
            startActivity(it)
        }

        imageBackground.setOnClickListener {
            IMAGE_BACKGROUND = 1
            selectImageInAlbum()
        }

        imageIcone.setOnClickListener {
            IMAGE_BACKGROUND = 0
            selectImageInAlbum()
        }
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
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
                    if (IMAGE_BACKGROUND == 0)
                        imageIcone.loadUrl(data?.data.toString())
                    else
                        imageBackground.loadUrl(data?.data.toString())
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
    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        private var IMAGE_BACKGROUND = 0
    }

}
