package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Consulta
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

class ConsultaService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getConsultasUsuario(usuarioId: Int) : List<Consulta> {
        val consultas: ArrayList<Consulta> = ArrayList()

        val (request, response, result) = "Consulta/usuario/$usuarioId".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            consultas.addAll(mapper.readValue<List<Consulta>>(data ?: ""))
        }

        return consultas
    }

    fun getConsultasProfissional(profissionalId: Int) : List<Consulta> {
        val consultas: ArrayList<Consulta> = ArrayList()

        val (request, response, result) = "Consulta/profissional/$profissionalId".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            consultas.addAll(mapper.readValue<List<Consulta>>(data ?: ""))
        }

        return consultas
    }

}