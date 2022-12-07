package br.ada.sayajins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.ada.sayajins.model.Pagamento;
import br.ada.sayajins.model.TipoPagamentoEnum;

public class LeitorCSV {
    
    /*
    * @author: João Alexandre
    */
    // Ler arquivo CSV com os dados de pagamento.
    public List<Pagamento> lerPagamentos(String nomeArquivo) throws Exception, IOException {

        List<Pagamento> list = new ArrayList<>();

        // lendo arquivo
        InputStream is = getClass().getClassLoader().getResourceAsStream(nomeArquivo);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        String linha;
        
        // Excluir a primeira linha com os nomes das colunas.
        br.readLine();

        while((linha = br.readLine()) != null) {

            // Lendo somente as linhas que tem conteúdo
            if (linha.trim() != null) {
                String[] partes = linha.split(";");

                // Exclui as linhas que não têm todas as colunas
                if (partes.length != 4) {
                    continue;
                }

                var p = DateTimeFormatter.ofPattern("yyyyMMdd");
                TipoPagamentoEnum tipo;
                
                switch (partes[1].toUpperCase()) {
                    case "FIDELIDADE": 
                        tipo = TipoPagamentoEnum.FIDELIDADE;
                        break;
                    case "CREDITO": 
                        tipo = TipoPagamentoEnum.CREDITO;
                        break;
                    case "DEBITO": 
                        tipo = TipoPagamentoEnum.DEBITO;
                        break;
                    case "BOLETO": 
                        tipo = TipoPagamentoEnum.BOLETO;
                        break;
                    case "PIX":
                        tipo = TipoPagamentoEnum.PIX;
                        break;
                    default:
                        throw new Exception("Deu ruim mah!!");
                }
                
                var item = new Pagamento(partes[0], LocalDate.parse(partes[2], p), Double.parseDouble(partes[3]), tipo);
                //System.out.println(item);
                list.add(item);
            }
            
        }

        br.close();
        return list;
    }

}
