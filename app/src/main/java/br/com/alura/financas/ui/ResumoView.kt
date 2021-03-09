package br.com.alura.financas.ui

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import br.com.alura.financas.R
import br.com.alura.financas.extension.formataParaBrasileiro
import br.com.alura.financas.model.Resumo
import br.com.alura.financas.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(context: Context, private val view: View?, transacoes: List<Transacao>) {

    private val resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza(){
        adiciona()
    }

    private fun adiciona() {
        val totalReceita = resumo.receita
        val totalDespesa = resumo.despesa
        val total: BigDecimal = resumo.total

        view?.let {
            with(it.resumo_card_receita) {
                setTextColor(corReceita)
                text = totalReceita.formataParaBrasileiro()
            }

            with(it.resumo_card_despesa) {
                setTextColor(corDespesa)
                text = totalDespesa.formataParaBrasileiro()
            }

            with(it.resumo_card_total) {
                val cor = corPor(total)
                setTextColor(cor)
                text = total.formataParaBrasileiro()
            }
        }
    }

    private fun corPor(valor: BigDecimal) = if (valor >= BigDecimal.ZERO) {
        corReceita
    } else {
        corDespesa
    }
}