package com.exucodeiro.veterinarioapp.Models

import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class Consulta (val consultaId: Int,
                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", locale = "pt_BR")
                     val data: Date,
                     val descricao: String,
                     val animalId: Int,
                     val animal: Animal,
                     val status: Int,
                     val profissionalId: Int,
                     val profissional: Profissional) : Serializable {

    companion object {
        val AGUARDANDO = 0
        val ACEITO = 1
        val RECUSADO = 2
        val REALIZADA = 3
    }

    fun getDataFormatada(): String {
        val local = Locale("pt", "BR")
        val df = SimpleDateFormat("EEEE',' dd 'de' MMMM 'de' yyyy 'as' HH:mm", local)
        return df.format(data)
    }
}