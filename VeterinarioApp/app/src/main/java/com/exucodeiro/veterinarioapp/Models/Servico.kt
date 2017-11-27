package com.exucodeiro.veterinarioapp.Models

import java.io.Serializable

data class Servico (val servicoId: Int,
                    val nome: String,
                    val descricao: String,
                    val requerCRV: Boolean) : Serializable {
    override fun toString(): String {
        return nome
    }

}