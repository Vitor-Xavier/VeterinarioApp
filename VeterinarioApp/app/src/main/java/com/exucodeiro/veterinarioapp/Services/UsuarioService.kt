package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Contato
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

/**
 * Created by vitor on 03/11/2017.
 */
class UsuarioService {

    fun getUsuario() : Usuario {
        var usuario: Usuario = Usuario(0, "Usuário", "Convidado", "https://cdn2.iconfinder.com/data/icons/medicine-4-1/512/vet-512.png", null, ArrayList<Contato>())
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        val (request, response, result) = "Usuario".httpGet().responseString()
        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            usuario = mapper.readValue<Usuario>(data ?: "")
        }

        return usuario
    }

    fun adicionaUsuario(usuario: Usuario) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.post("Usuario").body(mapper.writeValueAsString(usuario)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun atualizaUsuario(usuario: Usuario) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.put("Usuario").body(mapper.writeValueAsString(usuario)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun inativaUsuario(usuarioId: Int) : Boolean {
        var res = false
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.delete("Usuario/$usuarioId").response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun adicionaContato(usuarioId: Int, contato: Contato) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.post("Usuario/Contato/$usuarioId").body(mapper.writeValueAsString(contato)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

}