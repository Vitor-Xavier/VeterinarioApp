package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Contato
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.exucodeiro.veterinarioapp.Models.Servico
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost

class ProfissionalService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getProfissionais(latitude: Double, longitude: Double) : List<Profissional> {
        val profissionais: ArrayList<Profissional> = ArrayList()

        val (_, _, result) = "Profissional/$latitude/$longitude/".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            profissionais.addAll(mapper.readValue<List<Profissional>>(data ?: ""))
        }

        return profissionais
    }

    fun getProfissional(profissionalId: Int) : Profissional? {
        val (_, _, result) = "Profissional/$profissionalId".httpGet().responseString()
        val (data, error) = result

        val mapper = jacksonObjectMapper()
        when (error == null) {
            true -> return mapper.readValue(data ?: "")
            false -> return null
        }
    }

    fun postProfissional(profissional: Profissional) : Profissional? {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Profissional".httpPost().body(mapper.writeValueAsString(profissional)).responseString()
        val (data, error) = result

        return if (error == null) mapper.readValue(data ?: "") else null
    }

    fun inativaProfissional(profissionalId: Int) : Boolean {
        val (_, _, result) = "Profissional/$profissionalId".httpDelete().responseString()
        val (_, error) = result

        return (error == null)
    }

    fun adicionaServico(profissionalId: Int, servico: Servico) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Profissional/Servico/$profissionalId".httpPost().body(mapper.writeValueAsString(servico)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun adicionaContato(profissionalId: Int, contato: Contato) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Profissional/Contato/$profissionalId".httpPost().body(mapper.writeValueAsString(contato)).responseString()
        val (_, error) = result

        return (error == null)
    }

}