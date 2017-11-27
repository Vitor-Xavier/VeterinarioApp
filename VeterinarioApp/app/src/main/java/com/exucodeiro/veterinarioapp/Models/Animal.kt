package com.exucodeiro.veterinarioapp.Models

import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.util.*

data class Animal (val animalId: Int,
                   val nome: String,
                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", locale = "pt_BR")
                   val dataNascimento: Date?,
                   val imagem: String,
                   val tipoAnimalId: Int,
                   val tipoAnimal: TipoAnimal,
                   val usuarioId: Int,
                   val dono: Usuario?) : Serializable