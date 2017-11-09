package com.exucodeiro.veterinarioapp.Models

import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.util.*

/**
 * Created by vitor on 12/10/2017.
 */
data class Animal (val animalId: Int,
                   val nome: String,
                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                   val dataNascimento: Date?,
                   val imagem: String,
                   val tipoAnimalId: Int,
                   val tipoAnimal: TipoAnimal,
                   val usuarioId: Int,
                   val dono: Usuario?) : Serializable