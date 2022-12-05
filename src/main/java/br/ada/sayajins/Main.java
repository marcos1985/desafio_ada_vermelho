package br.ada.sayajins;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {

        var csvReader = new CSVReader();
        var list = csvReader.readPagamentos("pagamentos.csv");
        
        System.out.println(list);
        
    }

}
