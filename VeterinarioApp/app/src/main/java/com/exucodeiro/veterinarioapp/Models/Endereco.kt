package com.exucodeiro.veterinarioapp.Models

import java.io.Serializable

data class Endereco (val enderecoId: Int,
                     val logradouro: String,
                     val numero: Int,
                     val complemento: String,
                     val bairro: String,
                     val cep: String,
                     val cidade: String,
                     val estado: String,
                     val latitude: Double,
                     val longitude: Double) : Serializable {

    override fun toString(): String {
        return "$logradouro, $numero - $bairro\n$cidade - $estado"
    }

}