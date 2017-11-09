package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Contato
import com.exucodeiro.veterinarioapp.Models.Profissional
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.GsonDeserializer
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL

/**
 * Created by vitor on 03/11/2017.
 */
class ProfissionalService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getProfissionais(latitude: Double, longitude: Double) : List<Profissional> {
        val profissionais: ArrayList<Profissional> = ArrayList()

        val (request, response, result) = "Profissional/$latitude/$longitude/".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            profissionais.addAll(mapper.readValue<List<Profissional>>(data ?: ""))
        }

        return profissionais
    }

    fun postProfissional(profissional: Profissional) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false

        Fuel.post("Profissional").body(mapper.writeValueAsString(profissional)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun inativaProfissional(profissionalId: Int) : Boolean {
        var res = false

        Fuel.delete("Profissional/$profissionalId").response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun adicionaContato(profissionalId: Int, contato: Contato) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false

        Fuel.post("Profissional/Contato/$profissionalId").body(mapper.writeValueAsString(contato)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

}