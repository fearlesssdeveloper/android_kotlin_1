package br.com.alura.financas.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.com.alura.financas.R
import br.com.alura.financas.delegate.TransacaoDelegate
import br.com.alura.financas.extension.converteParaCalendar
import br.com.alura.financas.extension.formataParaBrasileiro
import br.com.alura.financas.model.Tipo
import br.com.alura.financas.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AlteraTransacaoDialog(private val viewGroup: ViewGroup, private val context: Context) {

    private val viewCriada = criaLayout()
    private val campoValor = viewCriada.form_transacao_valor
    private val campoData = viewCriada.form_transacao_data
    private val campoCategoria = viewCriada.form_transacao_categoria

    fun chama(transacao: Transacao, transacaoDelegate: TransacaoDelegate) {
        val tipo = transacao.tipo

        configuraCampoData(transacao)
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)

        campoValor.setText(transacao.valor.toString())
        campoData.setText(transacao.data.formataParaBrasileiro())
        val categoriasRetornadas = context.resources.getStringArray(categoriaPor(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }


    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton("Alterar") { _, _ ->
                val valorEmTexto = campoValor.text.toString()
                val dataEmTexto = campoData.text.toString()
                val categoriaEmTexto = campoCategoria.selectedItem.toString()

                var valor = converteCampoValor(valorEmTexto)
                val data = dataEmTexto.converteParaCalendar()

                val transacaoCriada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    data = data,
                    categoria = categoriaEmTexto
                )
                transacaoDelegate.delegate(transacaoCriada)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun tituloPor(tipo: Tipo) = if (tipo == Tipo.RECEITA) {
        R.string.altera_receita
    } else {
        R.string.altera_despesa
    }

    private fun converteCampoValor(valorEmTexto: String) = try {
        valorEmTexto.toBigDecimal()
    } catch (exception: NumberFormatException) {
        Toast.makeText(context, "Falha na conversão de valor", Toast.LENGTH_LONG).show()
        BigDecimal.ZERO
    }

    private fun configuraCampoCategoria(tipo: Tipo) {

        val categorias = categoriaPor(tipo)

        val adapter = ArrayAdapter.createFromResource(
            context,
            categorias,
            android.R.layout.simple_spinner_dropdown_item
        )

        campoCategoria.adapter = adapter
    }

    private fun categoriaPor(tipo: Tipo) = if (tipo == Tipo.RECEITA) {
        R.array.categorias_de_receita
    } else {
        R.array.categorias_de_despesa
    }

    private fun configuraCampoData(transacao: Transacao) {
        val hoje = transacao.data

        var anoAtual = hoje.get(Calendar.YEAR)
        var mesAtual = hoje.get(Calendar.MONTH)
        var diaAtual = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setOnClickListener {
            DatePickerDialog(
                context,
                { _, ano, mes, dia ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    anoAtual = ano
                    mesAtual = mes
                    diaAtual = dia
                    campoData.setText(dataSelecionada.formataParaBrasileiro())
                }, anoAtual, mesAtual, diaAtual
            ).show()
        }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context).inflate(R.layout.form_transacao, viewGroup, false)
    }
}