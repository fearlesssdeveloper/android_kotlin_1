package br.com.alura.financas.ui.activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.financas.R
import br.com.alura.financas.delegate.TransacaoDelegate
import br.com.alura.financas.model.Tipo
import br.com.alura.financas.model.Transacao
import br.com.alura.financas.ui.ResumoView
import br.com.alura.financas.ui.adapter.ListaTransacoesAdapter
import br.com.alura.financas.ui.dialog.AdicionaTransacaoDialog
import br.com.alura.financas.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes : MutableList<Transacao> = mutableListOf()
    private val viewDaActivity = window.decorView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFab()
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            chamaDialogDeAdicao(Tipo.RECEITA)
        }
        lista_transacoes_adiciona_despesa.setOnClickListener {
            chamaDialogDeAdicao(Tipo.DESPESA)
        }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(viewDaActivity as ViewGroup, this)
            .chama(tipo, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    adiciona(transacao)
                    lista_transacoes_adiciona_menu.close(true)
                }
            })
    }

    private fun adiciona(transacao: Transacao) {
        transacoes.add(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view: View = viewDaActivity
        ResumoView(this, view, transacoes).atualiza()
    }


    private fun configuraLista() {
        with(lista_transacoes_listview) {
            adapter = ListaTransacoesAdapter(transacoes, this@ListaTransacoesActivity)
            setOnItemClickListener { _, _, posicao, _ ->
                val transacao = transacoes[posicao]
                chamaDialogDeAlteracao(transacao, posicao)

            }
        }
    }

    private fun chamaDialogDeAlteracao(transacao: Transacao, posicao: Int) {
        AlteraTransacaoDialog(viewDaActivity as ViewGroup, this)
            .chama(transacao, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    altera(transacao, posicao)
                }

            })
    }

    private fun altera(transacao: Transacao, posicao: Int) {
        transacoes[posicao] = transacao
        atualizaTransacoes()
    }
}