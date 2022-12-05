package br.ada.sayajins;

import br.ada.sayajins.model.TipoPagamentoEnum;

public class Main {
    public static void main(String[] args) throws Exception {

        var leitorCSV = new LeitorCSV();
        var listaPagamentos = leitorCSV.lerPagamentos("pagamentos.csv");

        // System.out.println("------------------------------ TODOS -----------------------------------------");
        // System.out.println(listaPagamentos);

        // System.out.println("------------------------------ ATRASADOS -----------------------------------------");
        ProcessarPagamentos.processar(listaPagamentos);
        //System.out.println(listaPagamentos);

        ProcessarPagamentos.gerarArquivos(listaPagamentos, TipoPagamentoEnum.CREDITO);
        ProcessarPagamentos.gerarArquivos(listaPagamentos, TipoPagamentoEnum.DEBITO);
        ProcessarPagamentos.gerarArquivos(listaPagamentos, TipoPagamentoEnum.BOLETO);
        ProcessarPagamentos.gerarArquivos(listaPagamentos, TipoPagamentoEnum.FIDELIDADE);
        
    }

}
