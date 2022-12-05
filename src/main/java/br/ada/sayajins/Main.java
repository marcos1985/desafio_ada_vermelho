package br.ada.sayajins;

import br.ada.sayajins.model.TipoPagamentoEnum;

public class Main {
    public static void main(String[] args) throws Exception {

        var leitorCSV = new LeitorCSV();
        
        // Ler pagamentos do arquivo.
        var listaPagamentos = leitorCSV.lerPagamentos("pagamentos.csv");

        // Realiza o processamento conforme os requisitos.
        ProcessarPagamentos.processar(listaPagamentos);


        // Cria um arquivo para cada tipo de pagamento
        TipoPagamentoEnum[] tiposPagamento = {TipoPagamentoEnum.CREDITO, TipoPagamentoEnum.DEBITO, 
                                              TipoPagamentoEnum.BOLETO, TipoPagamentoEnum.FIDELIDADE};
        
        for(var tipo : tiposPagamento) {
            ProcessarPagamentos.gerarArquivos(listaPagamentos, tipo);
        }
        
    }

}
