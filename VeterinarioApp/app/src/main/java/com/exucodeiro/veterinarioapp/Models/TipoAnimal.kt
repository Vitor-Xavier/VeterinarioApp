package com.exucodeiro.veterinarioapp.Models

import java.io.Serializable

/**
 * Created by vitor on 12/10/2017.
 */
data class TipoAnimal (val tipoAnimalId: Int,
                       val tipo: String,
                       val icone: String) : Serializable