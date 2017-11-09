package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Contato
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

class UsuarioService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getUsuario(usuarioId: Int) : Usuario {
        var usuario = Usuario(0, "Usuário", "Convidado", "https://cdn2.iconfinder.com/data/icons/medicine-4-1/512/vet-512.png", null, ArrayList<Contato>())

        val (request, response, result) = "Usuario/$usuarioId".httpGet().responseString()

        val (data, error) = result
        val erro1 = error
        val data1 = data
        if (error == null) {
            val mapper = jacksonObjectMapper()
            usuario = mapper.readValue<Usuario>(data ?: "")
        }

        return usuario
    }

    fun adicionaUsuario(usuario: Usuario) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false

        Fuel.post("Usuario").body(mapper.writeValueAsString(usuario)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun atualizaUsuario(usuario: Usuario) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false

        Fuel.put("Usuario").body(mapper.writeValueAsString(usuario)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun inativaUsuario(usuarioId: Int) : Boolean {
        var res = false

        Fuel.delete("Usuario/$usuarioId").response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

    fun adicionaContato(usuarioId: Int, contato: Contato) : Boolean {
        val mapper = jacksonObjectMapper()
        var res = false

        Fuel.post("Usuario/Contato/$usuarioId").body(mapper.writeValueAsString(contato)).response { request, response, result ->
            val (data, error) = result
            res = (error == null)
        }
        return res
    }

}