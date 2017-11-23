package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Login
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

class LoginService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun logar(nomeUsuario: String, senha: String) : Login? {
        val (_, _, result) = "Login/$nomeUsuario/$senha".httpGet().responseString()
        val (data, error) = result

        val mapper = jacksonObjectMapper()
        when (error == null) {
            true -> return mapper.readValue(data ?: "")
            false -> return null
        }
    }
}