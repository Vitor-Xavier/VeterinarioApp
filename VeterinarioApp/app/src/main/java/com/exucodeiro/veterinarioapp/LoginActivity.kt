package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.exucodeiro.veterinarioapp.Services.LoginSettings
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import android.content.Intent
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.exucodeiro.veterinarioapp.Services.LoginService
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val settings = LoginSettings(this)
        inputUsuario.setText(settings.login.nomeUsuario, TextView.BufferType.EDITABLE)
        inputSenha.setText(settings.login.senha, TextView.BufferType.EDITABLE)

        buttonEntrar.setOnClickListener {
            async {
                val loginService = LoginService()
                val login = loginService.logar(inputUsuario.text.toString(), inputSenha.text.toString())

                if (login != null) {
                    settings.login = login

                    uiThread {
                        val intLg = Intent(this@LoginActivity, MainActivity::class.java)
                        intLg.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intLg)
                    }
                } else
                    uiThread {
                        toast("Não foi possível realizar o login.")
                    }
            }
        }

        fabNovo.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.add(Menu.NONE, 1, Menu.NONE, "Usuário")
        menu?.add(Menu.NONE, 2, Menu.NONE, "Profissional")
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            1 -> {
                val intentUsuario = Intent(this, CadastroUsuarioActivity::class.java)
                startActivity(intentUsuario)
            }
            2 -> {
                val intentPro = Intent(this, CadastroProfissionalActivity::class.java)
                startActivity(intentPro)
            }
        }
        return super.onContextItemSelected(item)
    }
}
