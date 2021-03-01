package br.com.alura.financas.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.financas.R
import br.com.alura.financas.extension.formataParaBrasileiro
import br.com.alura.financas.model.Tipo
import br.com.alura.financas.model.Transacao
import br.com.alura.financas.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.resumo_card.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        adicionaReceitaNoResumo(transacoes)

        configuraLista(transacoes)
    }

    private fun adicionaReceitaNoResumo(transacoes: List<Transacao>) {
        var totalReceita = BigDecimal.ZERO
        transacoes.forEach {
            if (it.tipo == Tipo.RECEITA) {
                totalReceita += it.valor
            }
        }
        resumo_card_receita.text = totalReceita.formataParaBrasileiro()
    }

    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> = listOf(
            Transacao(tipo = Tipo.DESPESA, categoria = "Almoço de final de semana", data = Calendar.getInstance(), valor = BigDecimal(20.5)),
            Transacao(valor = BigDecimal(100.0), categoria = "Econômia", tipo = Tipo.RECEITA),
            Transacao(valor = BigDecimal(200.0), tipo = Tipo.DESPESA),
            Transacao(valor = BigDecimal(500.0), categoria = "Prêmio", tipo = Tipo.RECEITA)
    )
}