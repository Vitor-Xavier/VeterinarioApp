package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Endereco
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager

/**
 * Created by vitor on 03/11/2017.
 */
class EnderecoService {

    fun atualizaEnderecoProfissional(profissionalId: Int, endereco: Endereco) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.put("Endereco/$profissionalId").body(mapper.writeValueAsString(endereco)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun atualizaEnderecoUsuario(usuarioId: Int, endereco: Endereco) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.put("Endereco/Usuario/$usuarioId").body(mapper.writeValueAsString(endereco)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }
}