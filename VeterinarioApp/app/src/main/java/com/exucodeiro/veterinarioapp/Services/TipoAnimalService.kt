package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.TipoAnimal
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.FuelManager

class TipoAnimalService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getTipoAnimais() : List<TipoAnimal> {
        val tipoAnimais = ArrayList<TipoAnimal>()

        val (_, _, result) = "TipoAnimal".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            tipoAnimais.addAll(mapper.readValue<List<TipoAnimal>>(data ?: ""))
        }

        return tipoAnimais
    }

    fun getTipoAnimal(tipoAnimalId: Int) : List<TipoAnimal> {
        val tipoAnimais = ArrayList<TipoAnimal>()

        val (_, _, result) = "TipoAnimal/$tipoAnimalId".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            tipoAnimais.addAll(mapper.readValue<List<TipoAnimal>>(data ?: ""))
        }

        return tipoAnimais
    }

    fun adicionaTipoAnimal(tipoAnimal: TipoAnimal) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "TipoAnimal".httpPost().body(mapper.writeValueAsString(tipoAnimal)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun atualizaTipoAnimal(tipoAnimal: TipoAnimal) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "TipoAnimal".httpPut().body(mapper.writeValueAsString(tipoAnimal)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun inativaTipoAnimal(tipoAnimalId: Int) : Boolean {
        val (_, _, result) = "TipoAnimal/$tipoAnimalId".httpDelete().responseString()
        val (_, error) = result

        return (error == null)
    }
}