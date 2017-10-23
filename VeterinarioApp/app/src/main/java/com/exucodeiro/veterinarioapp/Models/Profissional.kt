package com.exucodeiro.veterinarioapp.Models

/**
 * Created by vitor on 12/10/2017.
 */
data class Profissional (val profissionalId: Int,
                         val nome: String,
                         val sobrenome: String,
                         val imagem: String,
                         val crv: String,
                         val endereco: Endereco,
                         val contatos: List<Contato>,
                         val servicos: List<Servico>,
                         val ativo: Boolean)