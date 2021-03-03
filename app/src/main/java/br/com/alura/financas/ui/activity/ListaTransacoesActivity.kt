package br.com.alura.financas.ui.activity

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
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
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Calendar

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes : MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()

        configuraLista()

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

            val adapter = ArrayAdapter.createFromResource(this,
                    R.array.categorias_de_receita,
                    android.R.layout.simple_spinner_dropdown_item)

            viewCriada.form_transacao_categoria.adapter = adapter

            AlertDialog.Builder(this)
                    .setTitle(R.string.adiciona_receita)
                    .setView(viewCriada)
                    .setPositiveButton("Adicionar", DialogInterface.OnClickListener { dialogInterface, i ->
                        val valorEmTexto = viewCriada.form_transacao_valor.text.toString()
                        val dataEmTexto = viewCriada.form_transacao_data.text.toString()
                        val categoriaEmTexto = viewCriada.form_transacao_categoria.selectedItem.toString()

                        var valor = try {
                            valorEmTexto.toBigDecimal()
                        } catch (exception: NumberFormatException) {
                            Toast.makeText(this, "Falha na conversão de valor", Toast.LENGTH_LONG).show()
                            BigDecimal.ZERO
                        }

                        val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
                        val dataConvertida = formatoBrasileiro.parse(dataEmTexto)
                        val data = Calendar.getInstance()
                        data.time = dataConvertida
                        val transacaoCriada = Transacao(tipo = Tipo.RECEITA, valor = valor, data = data, categoria = categoriaEmTexto)
                        atualizaTransacoes(transacaoCriada)
                        lista_transacoes_adiciona_menu.close(true)
                    })
                    .setNegativeButton("Cancelar", null)
                    .show()
        }
    }

    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view: View = window.decorView
        ResumoView(this, view, transacoes).atualiza()
    }


    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }
}