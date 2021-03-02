package br.com.alura.financas.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.financas.R
import br.com.alura.financas.extension.formataParaBrasileiro
import br.com.alura.financas.model.Tipo
import br.com.alura.financas.model.Transacao
import br.com.alura.financas.ui.ResumoView
import br.com.alura.financas.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.Calendar

class ListaTransacoesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        configuraResumo(transacoes)

        configuraLista(transacoes)

        lista_transacoes_adiciona_receita.setOnClickListener {
            val view: View = window.decorView
            val viewCriada = LayoutInflater.from(this).inflate(R.layout.form_transacao, view as ViewGroup, false)

            val dataAtual = Calendar.getInstance()
            val ano = dataAtual.get(Calendar.YEAR)
            val mes = dataAtual.get(Calendar.MONTH)
            val dia = dataAtual.get(Calendar.DAY_OF_MONTH)

            val hoje = Calendar.getInstance().formataParaBrasileiro()
            viewCriada.form_transacao_data.setText(hoje)
            viewCriada.form_transacao_data.setOnClickListener {
                DatePickerDialog(this,
                        { view, ano, mes, dia ->
                            val dataSelecionada = Calendar.getInstance()
                            dataSelecionada.set(ano, mes, dia)
                            viewCriada.form_transacao_data.setText(dataSelecionada.formataParaBrasileiro())
                        }, ano, mes, dia)
                        .show()
            }
            AlertDialog.Builder(this)
                    .setTitle(R.string.adiciona_receita)
                    .setView(viewCriada)
                    .show()
        }
    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val view: View = window.decorView
        ResumoView(this, view, transacoes).atualiza()
    }


    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> = listOf(
            Transacao(tipo = Tipo.DESPESA, categoria = "Almoço de final de semana", data = Calendar.getInstance(), valor = BigDecimal(100.0)),
            Transacao(valor = BigDecimal(100.0), categoria = "Econômia", tipo = Tipo.RECEITA),
            Transacao(valor = BigDecimal(100.0), tipo = Tipo.DESPESA),
            Transacao(valor = BigDecimal(200.0), categoria = "Prêmio", tipo = Tipo.RECEITA)
    )
}