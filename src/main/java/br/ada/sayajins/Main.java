package br.ada.sayajins;

public class Main {
    public static void main(String[] args) throws Exception {

        var leitorCSV = new LeitorCSV();
        var listaPagamentos = leitorCSV.lerPagamentos("pagamentos.csv");

        System.out.println(listaPagamentos);
        
    }

}
