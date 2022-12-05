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

public class CSVReader {
    
    public List<Pagamento> readPagamentos(String fileName) throws Exception, IOException {

        List<Pagamento> list = new ArrayList<>();

        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        String line;
        
        br.readLine();

        while((line = br.readLine()) != null) {

            if (line.trim() != null) {
                String[] pieces = line.split(";");

                if (pieces.length != 4) {
                    continue;
                }

                System.out.println(Arrays.toString(pieces));
                //String s, LocalDate dateVencto, double valor, TipoPagamentoEnum tipoPagamentoEnum
                var p = DateTimeFormatter.ofPattern("yyyyMMdd");

                // Tipo
                //TipoPagamentoEnum tipo = TipoPagamentoEnum.BOLETO;
                TipoPagamentoEnum tipo;
                
                switch (pieces[1].toUpperCase()) {
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
                    default:
                        throw new Exception("Deu ruim mah!!");
                }
                
                var item = new Pagamento(pieces[0], LocalDate.parse(pieces[2], p), Double.parseDouble(pieces[3]), tipo); 
                list.add(item);
            }
            
        }

        br.close();
        return list;
    }

}
