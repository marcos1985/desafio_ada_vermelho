package br.ada.sayajins;

import br.ada.sayajins.model.TipoPagamentoEnum;

public class Main {
    public static void main(String[] args) throws Exception {

        var leitorCSV = new LeitorCSV();
        
        // Ler pagamentos do arquivo.
        var listaPagamentos = leitorCSV.lerPagamentos("pagamentos.csv");

        // Realiza o processamento conforme os requisitos.
        ProcessarPagamentos.processar(listaPagamentos);


        // Array com a lista de tipos de pagamento
        TipoPagamentoEnum[] tiposPagamento = {TipoPagamentoEnum.CREDITO, TipoPagamentoEnum.DEBITO, 
                                              TipoPagamentoEnum.BOLETO, TipoPagamentoEnum.FIDELIDADE, TipoPagamentoEnum.PIX};
        
        // Gera os arquivos CSV por tipo, um em cada thread.                                      
        for(var tipo : tiposPagamento) {
            Thread b = new Thread(new ThreadGerarCSV(listaPagamentos, tipo));
            b.start();
        }
        
    }

}
