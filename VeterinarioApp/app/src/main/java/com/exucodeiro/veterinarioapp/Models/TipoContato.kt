package com.exucodeiro.veterinarioapp.Models

import java.io.Serializable

/**
 * Created by vitor on 12/10/2017.
 */
data class TipoContato(val tipoContatoId: Int,
                       val nome: String,
                       val icone: String) : Serializable