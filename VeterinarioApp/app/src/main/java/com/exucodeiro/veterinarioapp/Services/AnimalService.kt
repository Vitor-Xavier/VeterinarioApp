package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Animal
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.FuelManager

/**
 * Created by vitor on 03/11/2017.
 */
class AnimalService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getAnimal(usuarioId: Int) : List<Animal> {
        val animais = ArrayList<Animal>()

        val (request, response, result) = "Animal/$usuarioId".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            animais.addAll(mapper.readValue<List<Animal>>(data ?: ""))
        }

        return animais
    }

    fun adicionaAnimal(animal: Animal) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false

//        Fuel.post("Animal/${animal.usuarioId}").body(mapper.writeValueAsString(animal)).response { request, response, result ->
//            val (data, error) = result
//            res = (error != null)
//        }
        val (request, response, result) = "Animal/${animal.usuarioId}".httpPost().body(mapper.writeValueAsString(animal)).responseString()
        val (data, error) = result
        res = (error != null)

        return res
    }

    fun atualizaAnimal(animal: Animal) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false

//        Fuel.put("Animal/${animal.usuarioId}").body(mapper.writeValueAsString(animal)).response { request, response, result ->
//            val (data, error) = result
//            res = (error != null)
//        }
        val (request, response, result) = "Animal/${animal.usuarioId}".httpPut().body(mapper.writeValueAsString(animal)).responseString()
        val (data, error) = result
        res = (error != null)

        return res
    }

    fun inativaAnimal(usuarioId: Int, animalId: Int) : Boolean {
        var res = false

        val (request, response, result) = "Animal/$usuarioId/$animalId".httpDelete().responseString()
        val (data, error) = result
        res = (error != null)

        return res
    }

}