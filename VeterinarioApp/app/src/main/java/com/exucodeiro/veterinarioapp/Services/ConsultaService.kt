package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Consulta
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut

class ConsultaService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getConsultasUsuario(usuarioId: Int) : List<Consulta> {
        val consultas: ArrayList<Consulta> = ArrayList()

        val (_, _, result) = "Consulta/Usuario/$usuarioId".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            consultas.addAll(mapper.readValue<List<Consulta>>(data ?: ""))
        }

        return consultas
    }

    fun getConsultasProfissional(profissionalId: Int) : List<Consulta> {
        val consultas: ArrayList<Consulta> = ArrayList()

        val (_, _, result) = "Consulta/Profissional/$profissionalId".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            consultas.addAll(mapper.readValue<List<Consulta>>(data ?: ""))
        }

        return consultas
    }

    fun adicionaConsulta(consulta: Consulta) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Consulta".httpPost().body(mapper.writeValueAsString(consulta)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun alteraConsulta(consulta: Consulta?) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Consulta".httpPut().body(mapper.writeValueAsString(consulta)).responseString()
        val (_, error) = result

        return (error == null)
    }

}