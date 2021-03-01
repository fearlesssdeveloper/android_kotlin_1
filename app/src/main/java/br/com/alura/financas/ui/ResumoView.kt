package br.com.alura.financas.ui

import android.view.View
import br.com.alura.financas.extension.formataParaBrasileiro
import br.com.alura.financas.model.Resumo
import br.com.alura.financas.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(private val view: View, transacoes: List<Transacao>) {

    private val resumo = Resumo(transacoes)

    fun adiciona() {
        val totalReceita = resumo.receita()
        val totalDespesa = resumo.despesa()
        val total: BigDecimal = resumo.total()

        view.resumo_card_receita.text = totalReceita.formataParaBrasileiro()
        view.resumo_card_despesa.text = totalDespesa.formataParaBrasileiro()
        view.resumo_card_total.text = total.formataParaBrasileiro()
    }
}