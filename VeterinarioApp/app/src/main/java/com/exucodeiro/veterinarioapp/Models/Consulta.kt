package com.exucodeiro.veterinarioapp.Models

import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by vitor on 23/10/2017.
 */
data class Consulta (val consultaId: Int,
                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", locale = "pt_BR")
                     val data: Date,
                     val descricao: String,
                     val animalId: Int,
                     val animal: Animal,
                     val profissionalId: Int,
                     val profissional: Profissional) : Serializable {

    fun getDataFormatada(): String {
        val local = Locale("pt", "BR")
        val df = SimpleDateFormat("EEEE',' dd 'de' MMMM 'de' yyyy 'as' HH:mm", local)
        return df.format(data)
    }
}