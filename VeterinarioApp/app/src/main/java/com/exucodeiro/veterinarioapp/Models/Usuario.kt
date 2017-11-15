package com.exucodeiro.veterinarioapp.Models

import java.io.Serializable

/**
 * Created by vitor on 12/10/2017.
 */
data class Usuario (val usuarioId: Int,
                    val nome: String,
                    val sobrenome: String,
                    val imagem: String,
                    val endereco: Endereco?,
                    val contatos: List<Contato>) : Serializable