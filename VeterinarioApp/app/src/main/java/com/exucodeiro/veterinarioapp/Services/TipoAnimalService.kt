package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.TipoAnimal
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

/**
 * Created by vitor on 08/11/2017.
 */
class TipoAnimalService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getTipoAnimais() : List<TipoAnimal> {
        val tipoAnimais = ArrayList<TipoAnimal>()

        val (request, response, result) = "TipoAnimal".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            tipoAnimais.addAll(mapper.readValue<List<TipoAnimal>>(data ?: ""))
        }

        return tipoAnimais
    }

    fun getTipoAnimal(tipoAnimalId: Int) : List<TipoAnimal> {
        val tipoAnimais = ArrayList<TipoAnimal>()

        val (request, response, result) = "TipoAnimal/$tipoAnimalId".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            tipoAnimais.addAll(mapper.readValue<List<TipoAnimal>>(data ?: ""))
        }

        return tipoAnimais
    }

    fun adicionaTipoAnimal(tipoAnimal: TipoAnimal) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false

        Fuel.post("TipoAnimal").body(mapper.writeValueAsString(tipoAnimal)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun atualizaTipoAnimal(tipoAnimal: TipoAnimal) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false

        Fuel.put("TipoAnimal").body(mapper.writeValueAsString(tipoAnimal)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun inativaTipoAnimal(tipoAnimalId: Int) : Boolean {
        var res = false

        Fuel.delete("TipoAnimal/$tipoAnimalId").response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }
}