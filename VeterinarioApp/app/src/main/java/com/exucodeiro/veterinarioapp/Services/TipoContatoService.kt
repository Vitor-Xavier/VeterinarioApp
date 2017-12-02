package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.TipoContato
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut

class TipoContatoService {

    init {
        FuelManager.instance.basePath = BaseService.BASE_URL
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getTiposContato() : List<TipoContato> {
        val tiposContato = ArrayList<TipoContato>()

        val (_, _, result) = "TipoContato".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            tiposContato.addAll(mapper.readValue<List<TipoContato>>(data ?: ""))
        }

        return tiposContato
    }

    fun adicionaTipoContato(tipoContato: TipoContato) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "TipoContato".httpPost().body(mapper.writeValueAsString(tipoContato)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun atualizaTipoContato(tipoContato: TipoContato) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "TipoContato".httpPut().body(mapper.writeValueAsString(tipoContato)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun inativaTipoContato(tipoContatoId: Int) : Boolean {
        val (_, _, result) = "TipoContato/$tipoContatoId".httpDelete().responseString()
        val (_, error) = result

        return (error == null)
    }

}