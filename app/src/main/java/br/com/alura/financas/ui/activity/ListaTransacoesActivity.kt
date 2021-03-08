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
        AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
            .chama(tipo, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    transacoes.add(transacao)
                    atualizaTransacoes()
                    lista_transacoes_adiciona_menu.close(true)
                }
            })
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view: View = window.decorView
        ResumoView(this, view, transacoes).atualiza()
    }


    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
        lista_transacoes_listview.setOnItemClickListener { parent, view, posicao, id ->
            val transacao = transacoes[posicao]
            AlteraTransacaoDialog(window.decorView as ViewGroup, this)
                .chama(transacao, object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        transacoes[posicao] = transacao
                        atualizaTransacoes()
                    }

                })

        }
    }
}