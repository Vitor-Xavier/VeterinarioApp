package com.exucodeiro.veterinarioapp.Models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Usuario (val usuarioId: Int,
                    val nome: String,
                    val sobrenome: String,
                    val nomeUsuario: String,
                    val senha: String,
                    val imagem: String,
                    var endereco: Endereco?,
                    val contatos: List<Contato>) : Serializable