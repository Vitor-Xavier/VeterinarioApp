package com.exucodeiro.veterinarioapp.Services

import com.exucodeiro.veterinarioapp.Models.Contato
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.FuelManager

class UsuarioService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getUsuario(usuarioId: Int) : Usuario {
        var usuario = Usuario(0, "Usuário", "Convidado", "", "", "https://cdn2.iconfinder.com/data/icons/medicine-4-1/512/vet-512.png", null, ArrayList<Contato>())

        val (_, _, result) = "Usuario/$usuarioId".httpGet().responseString()

        val (data, error) = result
        if (error == null) {
            val mapper = jacksonObjectMapper()
            usuario = mapper.readValue(data ?: "")
        }

        return usuario
    }

    fun adicionaUsuario(usuario: Usuario) : Usuario? {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Usuario".httpPost().body(mapper.writeValueAsString(usuario)).responseString()
        val (data, error) = result

        return if (error == null) mapper.readValue(data ?: "") else null
    }

    fun atualizaUsuario(usuario: Usuario) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Usuario".httpPut().body(mapper.writeValueAsString(usuario)).responseString()
        val (_, error) = result

        return (error == null)
    }

    fun inativaUsuario(usuarioId: Int) : Boolean {
        val (_, _, result) = "Usuario/$usuarioId".httpDelete().responseString()
        val (_, error) = result

        return (error == null)
    }

    fun adicionaContato(usuarioId: Int, contato: Contato) : Boolean {
        val mapper = jacksonObjectMapper()

        val (_, _, result) = "Usuario/Contato/$usuarioId".httpPost().body(mapper.writeValueAsString(contato)).responseString()
        val (_, error) = result

        return (error == null)
    }

}