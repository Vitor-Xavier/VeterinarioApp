package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Servico
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut

class ServicoService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getServicos() : List<Servico> {
        val servicos: ArrayList<Servico> = ArrayList()

        val (_, _, result) = "Servico".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            servicos.addAll(mapper.readValue<List<Servico>>(data ?: ""))
        }

        return servicos
    }

    fun adicionaServico(servico: Servico) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Servico".httpPost().body(mapper.writeValueAsString(servico)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun atualizaServico(servico: Servico) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Servico".httpPut().body(mapper.writeValueAsString(servico)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun associaServico(profissionalId: Int, servicoId: Int) : Boolean {
        val (_, _, result) = "Servico/$profissionalId/$servicoId".httpPost().responseString()
        val (_, error) = result

        return (error == null)
    }
}