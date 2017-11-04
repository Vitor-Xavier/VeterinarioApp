package com.exucodeiro.veterinarioapp.Models

import java.util.*

/**
 * Created by vitor on 12/10/2017.
 */
data class Animal (val animalId: Int,
                   val nome: String,
                   val dataNascimento: Calendar?,
                   val imagem: String,
                   val tipoAnimal: TipoAnimal?,
                   val usuarioId: Int,
                   val dono: Usuario?)