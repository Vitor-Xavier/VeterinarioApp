package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Login
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

class LoginService {

    init {
        FuelManager.instance.basePath = BaseService.BASE_URL
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun logar(nomeUsuario: String, senha: String) : Login? {
        val (_, _, result) = "Login/${nomeUsuario.trim()}/${senha.trim()}".httpGet().responseString()
        val (data, error) = result

        val mapper = jacksonObjectMapper()
        when (error == null) {
            true -> return try { mapper.readValue(data ?: "") } catch (e: Exception) { null }
            false -> return null
        }
    }

    fun verificaUsuario(nomeUsuario: String) : Boolean {
        val (_, _, result) = "Login/${nomeUsuario.trim()}".httpGet().responseString()
        val (data, error) = result

        val mapper = jacksonObjectMapper()
        when (error == null) {
            true -> return return try { mapper.readValue(data ?: "") } catch (e: Exception) { false }
            false -> return false
        }
    }

}