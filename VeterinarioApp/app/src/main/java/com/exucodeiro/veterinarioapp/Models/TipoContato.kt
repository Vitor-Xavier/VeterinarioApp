package com.exucodeiro.veterinarioapp.Models

import java.io.Serializable

data class TipoContato(val tipoContatoId: Int,
                       val nome: String,
                       val icone: String) : Serializable