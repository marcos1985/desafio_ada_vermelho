package br.ada.sayajins;

import br.ada.sayajins.model.TipoPagamentoEnum;
import br.ada.sayajins.utils.LeitorCSV;
import br.ada.sayajins.utils.ProcessarPagamentos;
import br.ada.sayajins.utils.ThreadGerarCSV;

public class Main {
    public static void main(String[] args) throws Exception {

        var leitorCSV = new LeitorCSV();
        
        // Ler pagamentos do arquivo.
        var listaPagamentos = leitorCSV.lerPagamentos("pagamentos.csv");

        // Realiza o processamento conforme os requisitos.
        ProcessarPagamentos.processar(listaPagamentos);
        //System.out.println(listaPagamentos);

        /*
        * @author: Isaac Neves Farias
        * Geração de arquivos na main
        */
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
