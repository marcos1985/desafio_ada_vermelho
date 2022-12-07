package br.ada.sayajins;

import br.ada.sayajins.model.TipoPagamentoEnum;
import br.ada.sayajins.utils.LeitorCSV;
import br.ada.sayajins.utils.ProcessarPagamentos;
import br.ada.sayajins.utils.ThreadGerarCSV;

/*  
*  @author: Isaac Neves Farias
*  Geração de arquivos via thread. Uma thread para cada tipo de pagamento.
*/ 
public class Main {
    public static void main(String[] args) throws Exception {

        // Instancia o Leitor de CSV de pagamentos
        var leitorCSV = new LeitorCSV();
        
        // Ler pagamentos do arquivo.
        var pagamentos = leitorCSV.lerPagamentos("pagamentos.csv");

        // Realiza o processamento conforme os requisitos.
        ProcessarPagamentos.processar(pagamentos);
        
        // Geração dos arquivos por tipo de pagamento
        for(var tipo : TipoPagamentoEnum.values()) {
            Thread b = new Thread(new ThreadGerarCSV(pagamentos, tipo));
            b.start();
        }
        
    }

}
