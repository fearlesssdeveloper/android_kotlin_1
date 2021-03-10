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

class AlteraTransacaoDialog(viewGroup: ViewGroup, private val context: Context) :
    FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    fun chama(transacao: Transacao, transacaoDelegate: TransacaoDelegate) {
        val tipo = transacao.tipo

        super.chama(tipo, transacaoDelegate)

        inicializaCampos(transacao)
    }

    private fun inicializaCampos(
        transacao: Transacao
    ) {
        val tipo = transacao.tipo
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(tipo, transacao)
    }

    private fun inicializaCampoCategoria(
        tipo: Tipo,
        transacao: Transacao
    ) {
        val categoriasRetornadas = context.resources.getStringArray(categoriaPor(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }

    override fun tituloPor(tipo: Tipo): Int = if (tipo == Tipo.RECEITA) {
        R.string.altera_receita
    } else {
        R.string.altera_despesa
    }
}