package br.ada.sayajins;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import br.ada.sayajins.model.Pagamento;
import br.ada.sayajins.model.TipoPagamentoEnum;

public class ProcessarPagamentos {
    
    public static void processar(List<Pagamento> pagamentos) {

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
                .forEach((pag) -> System.out.println(pag));

    }
}
