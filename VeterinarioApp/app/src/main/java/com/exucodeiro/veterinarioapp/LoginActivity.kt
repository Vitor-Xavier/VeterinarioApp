package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.exucodeiro.veterinarioapp.Models.Login
import com.exucodeiro.veterinarioapp.Services.LoginSettings
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import android.content.Intent
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
                        val it = Intent(this@LoginActivity, MainActivity::class.java)
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                } else
                    uiThread {
                        toast("Não foi possível realizar o login.")
                    }
            }

        }
    }
}
