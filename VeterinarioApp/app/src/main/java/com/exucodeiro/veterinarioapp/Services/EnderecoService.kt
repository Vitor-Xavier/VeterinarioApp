package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Endereco
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut

class EnderecoService {

    init {
        FuelManager.instance.basePath = BaseService.BASE_URL
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun atualizaEnderecoProfissional(profissionalId: Int, endereco: Endereco) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Endereco/Profissional/$profissionalId".httpPost().body(mapper.writeValueAsString(endereco)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun atualizaEnderecoUsuario(usuarioId: Int, endereco: Endereco) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Endereco/Usuario/$usuarioId".httpPost().body(mapper.writeValueAsString(endereco)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun getLatLng(endereco: Endereco) : Endereco? {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Endereco/LatLng".httpPost().body(mapper.writeValueAsString(endereco)).responseString()
        val (data, error) = result

        when (error == null) {
            true -> return mapper.readValue(data ?: "")
            false -> return null
        }
    }

    fun getEnderecoCompleto(latitude: Double, longitude: Double) : Endereco? {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Endereco/Completo/$latitude/$longitude/".httpGet().responseString()
        val (data, error) = result

        when (error == null) {
            true -> return mapper.readValue(data ?: "")
            false -> return null
        }
    }
}