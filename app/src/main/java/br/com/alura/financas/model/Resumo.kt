package br.com.alura.financas.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    fun receita(): BigDecimal {
        return transacoes.filter { transacao -> transacao.tipo == Tipo.RECEITA }
                .sumByDouble { transacao -> transacao.valor.toDouble() }
                .toBigDecimal()
    }

    fun despesa(): BigDecimal {
        return transacoes.filter { transacao -> transacao.tipo == Tipo.DESPESA }
                .sumByDouble { transacao -> transacao.valor.toDouble() }
                .toBigDecimal()
    }

    fun total(): BigDecimal {
        return receita().subtract(despesa())
    }
}

