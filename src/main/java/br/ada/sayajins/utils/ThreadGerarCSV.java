package br.ada.sayajins.utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.ada.sayajins.model.Pagamento;
import br.ada.sayajins.model.TipoPagamentoEnum;

/*
 * @autor: Marcos Alves de Oliveira
 * @autor: Gabriel Faustino Lima da Rocha
 * 
 * Classe criada para gerar os arquivos .csv por meio de thread
 */
public class ThreadGerarCSV implements Runnable {

    private BufferedWriter bw;
    private List<Pagamento> pagamentos;
    private TipoPagamentoEnum tipo;
    
    public ThreadGerarCSV(List<Pagamento> pagamentos, TipoPagamentoEnum tipo) throws IOException {
        var nomeArquivo = "PAGAMENTOS_" + tipo.toString() + "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".csv";
        this.bw = new BufferedWriter(new FileWriter(new File(nomeArquivo)));
        
        this.pagamentos = pagamentos;
        this.tipo = tipo;
    }

    @Override
    public void run() {
        
        //System.out.println("THREAD " + tipo.toString());
        
        try {
            bw.write("Nome;Tipo Pagamento;Data Vencimento;Valor\n");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        pagamentos.stream()
		.filter((pag) -> pag.getTipoPagamentoEnum() == this.tipo)
		.forEach((pag) -> {
			try {
				
				StringBuilder sb = new StringBuilder();
				sb.append(pag.getNome())
				.append(";")
                .append(pag.getTipoPagamentoEnum().toString())
				.append(";")
				.append(pag.getDtVencto().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
				.append(";")
				.append(pag.getValor().setScale(2, RoundingMode.CEILING))
				.append("\n");
				
				bw.write(sb.toString());
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				//System.out.println("AKjsjah");
			}
			
		});

        try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
}
