package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Animal
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.FuelManager

class AnimalService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getAnimais(usuarioId: Int) : List<Animal> {
        val animais = ArrayList<Animal>()

        val (_, _, result) = "Animal/$usuarioId".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            animais.addAll(mapper.readValue<List<Animal>>(data ?: ""))
        }

        return animais
    }

    fun adicionaAnimal(animal: Animal) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Animal/${animal.usuarioId}".httpPost().body(mapper.writeValueAsString(animal)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun atualizaAnimal(animal: Animal) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Animal/${animal.usuarioId}".httpPut().body(mapper.writeValueAsString(animal)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun inativaAnimal(usuarioId: Int, animalId: Int) : Boolean {
        val (_, _, result) = "Animal/$usuarioId/$animalId".httpDelete().responseString()
        val (_, error) = result

        return (error == null)
    }

}