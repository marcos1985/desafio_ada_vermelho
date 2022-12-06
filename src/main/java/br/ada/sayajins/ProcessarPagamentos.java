package br.ada.sayajins;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.ada.sayajins.model.Pagamento;
import br.ada.sayajins.model.TipoPagamentoEnum;

public class ProcessarPagamentos {
    
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
                });
                //.forEach((pag) -> System.out.println(pag));

            // Desconto por adiantamento
            var tiposAdiantamento = List.of(TipoPagamentoEnum.FIDELIDADE, TipoPagamentoEnum.PIX);
            //System.err.println("AJhsjhj");
            pagamentos.stream()
                    .filter((pag) -> tiposAdiantamento.contains(pag.getTipoPagamentoEnum()) && Period.between(pag.getDtVencto(), LocalDate.now()).getDays() < 0)
                    .map((pag) -> {
                        int dias = Math.abs(Period.between(pag.getDtVencto(), LocalDate.now()).getDays());
                        System.out.println("DIAS " + pag.getTipoPagamentoEnum().toString() + " : " + dias);
                        double taxa = 0.005 * dias;
                        
                        BigDecimal montante = pag.getValor()
                                                .multiply(BigDecimal.valueOf(1 - taxa));
                        pag.setValor(montante);
                        return pag;
                    });
                    //.forEach(System.out::println);
    }

    public static void gerarArquivos(List<Pagamento> pagamentos, TipoPagamentoEnum tipo) throws IOException {
        BufferedWriter bw;
        
        var nomeArquivo = "PAGAMENTOS_" + tipo.toString() + "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".csv";
        
        bw = new BufferedWriter(new FileWriter(new File(nomeArquivo))); 
        bw.write("Nome;Tipo Pagamento;Data Vencimento;Valor\n");
        
        pagamentos.stream()
            .filter((pag) -> pag.getTipoPagamentoEnum() == tipo)
            .forEach((pag) -> {
                var str = pag.getNome() + ";" + pag.getTipoPagamentoEnum().toString() + ";" + pag.getDtVencto().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ";" + pag.getValor();
                try {
                    bw.write(str);
                    bw.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            });

        bw.close();

    }
}
