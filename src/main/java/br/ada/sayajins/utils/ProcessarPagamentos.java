package br.ada.sayajins.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.ada.sayajins.model.Pagamento;
import br.ada.sayajins.model.TipoPagamentoEnum;

public class ProcessarPagamentos {
    
    /*
    * @author: João Vitor Siqueira Resende
    * processamento dos pagamentos.
    */
    public static void processar(List<Pagamento> pagamentos) {
        
        // Vencidos
        pagamentos.stream()
                .filter((pag) -> pag.getDtVencto().isBefore(LocalDate.now()))
                .map((pag) -> {
                    
                    int meses = Period.between(pag.getDtVencto(), LocalDate.now()).getMonths();
                    double taxa = 0;

                    if (pag.getTipoPagamentoEnum() == TipoPagamentoEnum.CREDITO) {
                        taxa = 0.03;
                    } else if (pag.getTipoPagamentoEnum() == TipoPagamentoEnum.DEBITO) {
                        taxa = 0.01;
                    } else if (pag.getTipoPagamentoEnum() == TipoPagamentoEnum.BOLETO) {
                        taxa = 0.05;
                    }
                    
                    BigDecimal montante = pag.getValor()
                                            .multiply(BigDecimal.valueOf(Math.pow(1 + taxa, meses)));
                    pag.setValor(montante);
                    return pag;
                })
                .count();
                //.forEach((pag) -> System.out.println(pag));

            
            // Desconto por adiantamento
            var tiposAdiantamento = List.of(TipoPagamentoEnum.FIDELIDADE, TipoPagamentoEnum.PIX);
            
            pagamentos.stream()
                    .filter((pag) -> tiposAdiantamento.contains(pag.getTipoPagamentoEnum()) && Period.between(pag.getDtVencto(), LocalDate.now()).getDays() < 0)
                    .map((pag) -> {
                        int dias = Math.abs(Period.between(pag.getDtVencto(), LocalDate.now()).getDays());
                        double taxa = 0.005 * dias;
                        
                        BigDecimal montante = pag.getValor()
                                                .multiply(BigDecimal.valueOf(1 - taxa));
                        pag.setValor(montante);
                        return pag;
                    })
                    .count();
                    //.forEach(System.out::println);
    }

}
