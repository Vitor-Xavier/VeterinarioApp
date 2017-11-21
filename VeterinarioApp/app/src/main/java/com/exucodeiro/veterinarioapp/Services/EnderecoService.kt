package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Endereco
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost

/**
 * Created by vitor on 03/11/2017.
 */
class EnderecoService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun atualizaEnderecoProfissional(profissionalId: Int, endereco: Endereco) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false

        Fuel.put("Endereco/$profissionalId").body(mapper.writeValueAsString(endereco)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun atualizaEnderecoUsuario(usuarioId: Int, endereco: Endereco) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false

        Fuel.put("Endereco/Usuario/$usuarioId").body(mapper.writeValueAsString(endereco)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun GetLatLng(endereco: Endereco) : Endereco? {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Endereco/LatLng".httpPost().body(mapper.writeValueAsString(endereco)).responseString()
        val (data, error) = result

        when (error == null) {
            true -> return mapper.readValue(data ?: "")
            false -> return null
        }
    }

    fun GetEnderecoCompleto(latitude: Double, longitude: Double) : Endereco? {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Endereco/Completo/$latitude/$longitude/".httpGet().responseString()
        val (data, error) = result

        when (error == null) {
            true -> return mapper.readValue(data ?: "")
            false -> return null
        }
    }
}