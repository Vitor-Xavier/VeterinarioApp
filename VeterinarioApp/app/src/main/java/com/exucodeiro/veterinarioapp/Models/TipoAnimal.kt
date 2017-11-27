package com.exucodeiro.veterinarioapp.Models

import java.io.Serializable

data class TipoAnimal (val tipoAnimalId: Int,
                       val tipo: String,
                       val icone: String) : Serializable