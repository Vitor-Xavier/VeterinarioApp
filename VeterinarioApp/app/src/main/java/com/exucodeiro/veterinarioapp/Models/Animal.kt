package com.exucodeiro.veterinarioapp.Models

import java.time.LocalDate

/**
 * Created by vitor on 12/10/2017.
 */
data class Animal (val animalId: Int,
                   val nome: String,
                   val dataNascimento: LocalDate,
                   val imagem: String, val tipoAnimal: TipoAnimal,
                   val dono: Usuario)