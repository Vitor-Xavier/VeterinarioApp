package com.exucodeiro.veterinarioapp.Models

import java.io.Serializable

/**
 * Created by vitor on 12/10/2017.
 */
data class Servico (val servicoId: Int,
                    val nome: String,
                    val descricao: String,
                    val requerCRV: Boolean) : Serializable {
    override fun toString(): String {
        return nome
    }

}