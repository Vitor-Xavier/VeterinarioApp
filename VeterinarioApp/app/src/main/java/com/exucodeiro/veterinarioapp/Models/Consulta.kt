package com.exucodeiro.veterinarioapp.Models

import java.util.*

/**
 * Created by vitor on 23/10/2017.
 */
data class Consulta (val consultaId: Int,
                     val data: Calendar,
                     val descricao: String,
                     val animal: Animal,
                     val profissional: Profissional) {

    fun getData(): String {
        var diaSemana = ""
        when (data.get(Calendar.DAY_OF_WEEK)) {
            1 -> diaSemana = "Domingo"
            2 -> diaSemana = "Segunda-feira"
            3 -> diaSemana = "Terça-feira"
            4 -> diaSemana = "Quarta-feira"
            5 -> diaSemana = "Quinta-feira"
            6 -> diaSemana = "Sexta-feira"
            7 -> diaSemana = "Sábado"
        }
        var mes = ""
        when (data.get(Calendar.MONTH)) {
            1 -> mes = "janeiro"
            2 -> mes = "fevereiro"
            3 -> mes = "março"
            4 -> mes = "abril"
            5 -> mes = "maio"
            6 -> mes = "junho"
            7 -> mes = "julho"
            8 -> mes = "agosto"
            9 -> mes = "setembro"
            10 -> mes = "outubro"
            11 -> mes = "novembro"
            12 -> mes = "dezembro"
        }
        return "$diaSemana, " + data.get(Calendar.DAY_OF_MONTH) + " de $mes de " + data.get(Calendar.YEAR)
    }
}