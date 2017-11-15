package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Login
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

/**
 * Created by vitor on 12/11/2017.
 */
class LoginService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun logar(nomeUsuario: String, senha: String) : Login? {
        var login: Login? = null

        val (request, response, result) = "Login/$nomeUsuario/$senha".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            login = mapper.readValue(data ?: "")
        }

        return login
    }
}