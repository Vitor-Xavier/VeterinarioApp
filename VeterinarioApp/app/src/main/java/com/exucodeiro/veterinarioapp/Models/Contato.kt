package com.exucodeiro.veterinarioapp.Models

/**
 * Created by vitor on 12/10/2017.
 */
data class Contato(val contatoId: Int,
                  val texto: String,
                  val principal: Boolean,
                  val tipoContato: TipoContato)