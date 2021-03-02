package br.com.alura.financas.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    fun receita(): BigDecimal = somaPor(Tipo.RECEITA)
    

    fun despesa(): BigDecimal = somaPor(Tipo.DESPESA)


    fun total(): BigDecimal = receita().subtract(despesa())

    private fun somaPor(tipo: Tipo): BigDecimal = transacoes.filter { it.tipo == tipo }
                .sumByDouble { it.valor.toDouble() }
                .toBigDecimal()

    
}

