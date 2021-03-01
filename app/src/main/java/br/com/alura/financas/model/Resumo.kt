package br.com.alura.financas.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {
    private var totalReceita: BigDecimal = BigDecimal.ZERO
    private var totalDespesa: BigDecimal = BigDecimal.ZERO

    fun receita(): BigDecimal {
        transacoes.forEach {
            if (it.tipo == Tipo.RECEITA)
                totalReceita += it.valor
        }
        return totalReceita
    }

    fun despesa(): BigDecimal {
        transacoes.forEach {
            if (it.tipo == Tipo.DESPESA)
                totalDespesa += it.valor
        }
        return totalDespesa
    }

    fun total(): BigDecimal {
        return totalReceita.subtract(totalDespesa)
    }
}

