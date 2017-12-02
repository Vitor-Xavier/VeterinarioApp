package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Contato
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpPut

class ContatoService {

    init {
        FuelManager.instance.basePath = BaseService.BASE_URL
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun atualizaContato(contato: Contato) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Contato".httpPut().body(mapper.writeValueAsString(contato)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun inativaContato(contatoId: Int) : Boolean {
        val (_, _, result) = "Contato/$contatoId".httpDelete().responseString()
        val (_, error) = result

        return (error == null)
    }

}