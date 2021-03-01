package br.com.alura.financas.ui

import android.view.View
import br.com.alura.financas.extension.formataParaBrasileiro
import br.com.alura.financas.model.Tipo
import br.com.alura.financas.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(private val view: View) {
    fun adiciona(transacoes: List<Transacao>) {
        var totalReceita = BigDecimal.ZERO
        var totalDespesa = BigDecimal.ZERO
        transacoes.forEach {
            if (it.tipo == Tipo.RECEITA) {
                totalReceita += it.valor
            } else {
                totalDespesa += it.valor
            }
        }
        view.resumo_card_receita.text = totalReceita.formataParaBrasileiro()
        view.resumo_card_despesa.text = totalDespesa.formataParaBrasileiro()
    }
}