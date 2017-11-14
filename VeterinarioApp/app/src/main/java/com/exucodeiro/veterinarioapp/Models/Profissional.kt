package com.exucodeiro.veterinarioapp.Models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

/**
 * Created by vitor on 12/10/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Profissional (val profissionalId: Int,
                         val nome: String,
                         val sobrenome: String,
                         val imagem: String,
                         val icone: String,
                         val crv: String,
                         val enderecoId: Int,
                         var endereco: Endereco?,
                         val contatos: List<Contato>?,
                         val servicos: List<Servico>,
                         val online: Boolean) : Serializable