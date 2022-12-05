package br.ada.sayajins;

public class Main {
    public static void main(String[] args) throws Exception {

        var leitorCSV = new LeitorCSV();
        var listaPagamentos = leitorCSV.lerPagamentos("pagamentos.csv");

        System.out.println("------------------------------ TODOS -----------------------------------------");
        System.out.println(listaPagamentos);

        System.out.println("------------------------------ ATRASADOS -----------------------------------------");
        ProcessarPagamentos.processar(listaPagamentos);
        //System.out.println(listaPagamentos);
        
    }

}
