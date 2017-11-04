package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Servico
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

/**
 * Created by vitor on 03/11/2017.
 */
class ServicoService {

    fun getServicos() : List<Servico> {
        val servicos: ArrayList<Servico> = ArrayList()
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        val (request, response, result) = "Servico".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            servicos.addAll(mapper.readValue<List<Servico>>(data ?: ""))
        }

        return servicos
    }

    fun adicionaServico(servico: Servico) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.post("Servico").body(mapper.writeValueAsString(servico)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun atualizaServico(servico: Servico) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.put("Servico").body(mapper.writeValueAsString(servico)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun associaServico(profissionalId: Int, servicoId: Int) : Boolean {
        var res = false
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.post("Servico/$profissionalId/$servicoId").response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }
}