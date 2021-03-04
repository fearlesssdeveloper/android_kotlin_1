package br.com.alura.financas.delegate

import br.com.alura.financas.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacao: Transacao)
}