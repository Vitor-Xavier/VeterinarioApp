package com.exucodeiro.veterinarioapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.exucodeiro.veterinarioapp.Models.Login
import com.exucodeiro.veterinarioapp.Services.LoginSettings
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val settings = LoginSettings(this)
        inputUsuario.setText(settings.login.username, TextView.BufferType.EDITABLE)
        inputSenha.setText(settings.login.password, TextView.BufferType.EDITABLE)

        buttonEntrar.setOnClickListener {
            settings.login = Login(1, inputUsuario.text.toString(), inputSenha.text.toString(), "teste")

            toast(settings.login.username)
        }
    }
}
