package br.com.alura.financas.extension

fun String.limitaEmAte(caracteres: Int) : String{
    if (this.length > caracteres) {
        return "${this.substring(0, caracteres)}..."
    }
    return this
}