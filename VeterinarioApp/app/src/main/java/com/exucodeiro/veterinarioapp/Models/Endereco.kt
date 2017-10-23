package com.exucodeiro.veterinarioapp.Models

/**
 * Created by vitor on 12/10/2017.
 */
data class Endereco (val enderecoId: Int,
                    val logradouro: String,
                    val numero: Int,
                    val complemento: String,
                    val bairro: String,
                    val cep: String,
                    val cidade: String,
                    val estado: String) {

    override fun toString(): String {
        return "$logradouro, $numero - $bairro\n$cidade - $estado"
    }

}