package com.exucodeiro.veterinarioapp.Models

import java.io.Serializable

/**
 * Created by vitor on 12/10/2017.
 */
data class Contato(val contatoId: Int,
                   val texto: String,
                   val principal: Boolean,
                   val tipoContatoId: Int,
                   val tipoContato: TipoContato) : Serializable {
    override fun toString(): String {
        return texto
    }

}